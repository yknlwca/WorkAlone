package com.ssafy.workalone.mlkit.java

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.annotation.KeepName
import com.google.mlkit.common.MlKitException
import com.ssafy.workalone.R
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.data.local.SettingsPreferenceManager
import com.ssafy.workalone.mlkit.CameraXViewModel
import com.ssafy.workalone.mlkit.GraphicOverlay
import com.ssafy.workalone.mlkit.VisionImageProcessor
import com.ssafy.workalone.mlkit.java.posedetector.PoseDetectorProcessor
import com.ssafy.workalone.mlkit.preference.PreferenceUtils
import com.ssafy.workalone.presentation.ui.screen.exercise.ExerciseMLkitView
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModelFactory
import com.ssafy.workalone.presentation.viewmodels.member.MemberViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@KeepName
@RequiresApi(VERSION_CODES.LOLLIPOP)
class CameraXLivePreviewActivity : AppCompatActivity(), OnItemSelectedListener,
    CompoundButton.OnCheckedChangeListener {

    private var previewView: PreviewView? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null
    private var imageProcessor: VisionImageProcessor? = null
    private var needUpdateGraphicOverlayImageSourceInfo = false
    private var selectedModel = POSE_DETECTION
    private var lensFacing = CameraSelector.LENS_FACING_FRONT
    private var cameraSelector: CameraSelector? = null
    private val exerciseViewModel: ExerciseMLKitViewModel by viewModels {
        ExerciseMLKitViewModelFactory(this)
    }
    private val memberViewModel : MemberViewModel by viewModels()

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null // 최적화 -> 뷰 모델 이동
    private val isInitRecording = MutableLiveData(false) // Recording 상태 감지

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preferenceManger: ExerciseInfoPreferenceManager
    private lateinit var settingManager: SettingsPreferenceManager
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        preferenceManger = ExerciseInfoPreferenceManager(applicationContext)
        settingManager = SettingsPreferenceManager(applicationContext)

        setContentView(R.layout.activity_vision_camerax_live_preview)

        previewView = findViewById(R.id.preview_view)
        graphicOverlay = findViewById(R.id.graphic_overlay)

        cameraExecutor = Executors.newSingleThreadExecutor()

        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CameraXViewModel::class.java).processCameraProvider.observe(this) { provider ->
            cameraProvider = provider
            bindAllCameraUseCases()
            if (settingManager.getRecordingMode()) {
                captureVideo()
            }
        }
        exerciseViewModel.exerciseType.observe(this) { newExerciseType ->
            // exerciseType이 변경될 때마다 bindAnalysisUseCase 호출
            if(newExerciseType != null){
                bindAnalysisUseCase()
            }

        }


        if (savedInstanceState != null) {
            selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, POSE_DETECTION)
        }
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        if (previewView == null) {
            Log.d(TAG, "previewView is null")
        }
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null")
        }

        isInitRecording.observe(this) {
            //ComposeView설정
            val composeView: ComposeView = findViewById(R.id.compose_view)
            composeView.setContent {
                ExerciseMLkitView(
                    viewModel = exerciseViewModel,
                    recording = if (it) recording else null
                )
            }
        }

        val options: MutableList<String> = ArrayList()
        options.add(POSE_DETECTION)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString("STATE_SELECTED_MODEL", selectedModel)
    }

    @Synchronized
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedModel = parent?.getItemAtPosition(pos).toString()
        Log.d(TAG, "Selected model: $selectedModel")
        bindAnalysisUseCase()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing.
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (cameraProvider == null) {
            return
        }
        val newLensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
            CameraSelector.LENS_FACING_BACK
        } else {
            CameraSelector.LENS_FACING_FRONT
        }
        val newCameraSelector = CameraSelector.Builder().requireLensFacing(newLensFacing).build()
        try {
            if (cameraProvider!!.hasCamera(newCameraSelector)) {
                Log.d(TAG, "Set facing to " + newLensFacing)
                lensFacing = newLensFacing
                cameraSelector = newCameraSelector
                bindAllCameraUseCases()
                return
            }
        } catch (e: CameraInfoUnavailableException) {
            // Falls through
        }
        Toast.makeText(
            applicationContext,
            "This device does not have lens with facing: $newLensFacing",
            Toast.LENGTH_SHORT,
        ).show()
    }

    public override fun onResume() {
        super.onResume()
        bindAllCameraUseCases()
    }

    override fun onPause() {
        super.onPause()
        imageProcessor?.run { this.stop() }
    }

    public override fun onDestroy() {
        super.onDestroy()
        imageProcessor?.run { this.stop() }

    }

    private fun bindAllCameraUseCases() {

        if (cameraProvider != null) {
            // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
            cameraProvider!!.unbindAll()
            bindPreviewUseCase()
            bindAnalysisUseCase()
            bindVideoCaptureUseCase()
        }
    }

    private fun bindPreviewUseCase() {
        if (!PreferenceUtils.isCameraLiveViewportEnabled(this)) {
            return
        }
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider!!.unbind(previewUseCase)
        }

        val builder = Preview.Builder()
        val targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing)
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution)
        }
        previewUseCase = builder.build()
        previewUseCase!!.setSurfaceProvider(previewView!!.getSurfaceProvider())
        camera = cameraProvider!!.bindToLifecycle(this, cameraSelector!!, previewUseCase)
    }


    private fun bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider!!.unbind(analysisUseCase)
        }
        if (imageProcessor != null) {
            imageProcessor!!.stop()
        }
        imageProcessor =
            try {
                val poseDetectorOptions = PreferenceUtils.getPoseDetectorOptionsForLivePreview(this)
                val shouldShowInFrameLikelihood =
                    PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this)
                val visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this)
                val rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this)
                val runClassification = true
//                PreferenceUtils.shouldPoseDetectionRunClassification(this)
                PoseDetectorProcessor(
                    this,
                    poseDetectorOptions,
                    shouldShowInFrameLikelihood,
                    visualizeZ,
                    rescaleZ,
                    runClassification,
                    /* isStreamMode = */ true,
                    exerciseViewModel.exerciseType.value,
                    exerciseViewModel
                )
            } catch (e: Exception) {
                Log.e(TAG, "Can not create image processor: $selectedModel", e)
                Toast.makeText(
                    applicationContext,
                    "Can not create image processor: " + e.localizedMessage,
                    Toast.LENGTH_LONG,
                )
                    .show()
                return
            }
        //새로운 분석 설정 및 바인딩
        val builder = ImageAnalysis.Builder()
        val targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing)
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution)
        }
        analysisUseCase = builder.build()

        needUpdateGraphicOverlayImageSourceInfo = true

        analysisUseCase?.setAnalyzer(
            // imageProcessor.processImageProxy will use another thread to run the detection underneath,
            // thus we can just runs the analyzer itself on main thread.
            ContextCompat.getMainExecutor(this),
            ImageAnalysis.Analyzer { imageProxy: ImageProxy ->

                if (needUpdateGraphicOverlayImageSourceInfo) {
                    val isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT
                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                    if (rotationDegrees == 0 || rotationDegrees == 180) {
                        graphicOverlay!!.setImageSourceInfo(
                            imageProxy.width,
                            imageProxy.height,
                            isImageFlipped
                        )
                    } else {
                        graphicOverlay!!.setImageSourceInfo(
                            imageProxy.height,
                            imageProxy.width,
                            isImageFlipped
                        )
                    }
                    needUpdateGraphicOverlayImageSourceInfo = false
                }
                try {
                    imageProcessor!!.processImageProxy(imageProxy, graphicOverlay)
                } catch (e: MlKitException) {
                    Log.e(TAG, "Failed to process image. Error: " + e.localizedMessage)
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            },
        )
        cameraProvider!!.bindToLifecycle(this, cameraSelector!!, analysisUseCase)
    }


    private fun bindVideoCaptureUseCase() {
        val recorder = Recorder.Builder()
            .setQualitySelector(
                QualitySelector.from(
                    Quality.HIGHEST,
                    FallbackStrategy.higherQualityOrLowerThan(Quality.SD)
                )
            )
            .build()
        videoCapture = VideoCapture.withOutput(recorder)
        cameraProvider?.bindToLifecycle(this, cameraSelector(), videoCapture)
    }

    private fun cameraSelector(): CameraSelector {
        return CameraSelector.Builder().requireLensFacing(lensFacing).build()
    }

    private fun captureVideo() {
        // 현재 VideoCapture 객체의 참조를 확인하거나 초기화되지 않았으면 함수를 종료한다.
        val videoCapture = this.videoCapture ?: return

        // VideoRecordListener에서 중복 녹화를 방지하기 위해 다시 설정 된다.
        val curRecording = recording
        // 진행 중인 활성 녹화 세션이 있으면 중지하고 현재 recording 자원을 해제한다.
        if (curRecording != null) {
            curRecording.stop()
            recording = null
            return
        }

        // create and start a new recording session
        // 녹화를 시작하기 위해 비디오 녹화를 위한 파일 이름을 생성한다.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA)
            .format(System.currentTimeMillis())

        // 비디오 파일의 메타데이터를 설정한다.
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        // 콘텐츠의 외부 저장 위치를 옵션으로 설정하기 위해 빌더를 만들고 인스턴스를 빌드한다.
        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        // 비디오 캡처 출력 옵션을 설정하고 녹화 영상 출력을 위한 세션을 만든다.
        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .apply {
                withAudioEnabled()
            }
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                // 새 녹음을 시작하고 리스너를 등록
                when (recordEvent) {
                    // 녹화 종료
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val msg = "저장 : ${recordEvent.outputResults.outputUri}"
                            Toast.makeText(this@CameraXLivePreviewActivity, msg, Toast.LENGTH_SHORT).show()
                            // 파일의 URI
                            val fileUri = recordEvent.outputResults.outputUri
                            preferenceManger.setFileUrl(fileUri.toString())
                            recording?.close()
                            recording = null
                            Log.d("FILE URI", fileUri.toString())
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: ${recordEvent.error}")
                        }
                    }
                }
            }
        isInitRecording.value = true
    }


    companion object {
        private const val TAG = "CameraXLivePreview"
        private const val POSE_DETECTION = "Pose Detection"

        private const val STATE_SELECTED_MODEL = "selected_model"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
