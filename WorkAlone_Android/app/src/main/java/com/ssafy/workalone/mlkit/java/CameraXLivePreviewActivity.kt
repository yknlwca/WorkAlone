package com.ssafy.workalone.mlkit.java

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES
import android.os.Bundle
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
import androidx.camera.view.PreviewView
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.annotation.KeepName
import com.google.mlkit.common.MlKitException
import com.ssafy.workalone.R
import com.ssafy.workalone.mlkit.CameraXViewModel
import com.ssafy.workalone.mlkit.GraphicOverlay
import com.ssafy.workalone.mlkit.VisionImageProcessor
import com.ssafy.workalone.mlkit.java.posedetector.PoseDetectorProcessor
import com.ssafy.workalone.mlkit.preference.PreferenceUtils
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel
import com.ssafy.workalone.presentation.ui.screen.ExerciseMLkitView
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModelFactory


@KeepName
@RequiresApi(VERSION_CODES.LOLLIPOP)
class CameraXLivePreviewActivity :
  AppCompatActivity(), OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

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




  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)

    //쉬는시간 상태 관찰
//    exerciseViewModel.isResting.observe(this,Observer{isResting ->
//        if(!isResting)
//        //카메라 분석 시작
        bindAllCameraUseCases()
//    })



    if (savedInstanceState != null) {
      selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, POSE_DETECTION)
    }
    cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    setContentView(R.layout.activity_vision_camerax_live_preview)



    previewView = findViewById(R.id.preview_view)
    if (previewView == null) {
      Log.d(TAG, "previewView is null")
    }
    graphicOverlay = findViewById(R.id.graphic_overlay)
    if (graphicOverlay == null) {
      Log.d(TAG, "graphicOverlay is null")
    }

    //ComposeView설정
    val composeView: ComposeView = findViewById(R.id.compose_view)
    val exerciseType = exerciseViewModel.exerciseType.value
    composeView.setContent {
      ExerciseMLkitView(exerciseType,exerciseViewModel)
    }

    val options: MutableList<String> = ArrayList()
    options.add(POSE_DETECTION)



    ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
      .get(CameraXViewModel::class.java)
      .processCameraProvider
      .observe(
        this,
        Observer { provider: ProcessCameraProvider? ->
          cameraProvider = provider
          bindAllCameraUseCases()
        },
      )
  }
  override fun onSaveInstanceState(bundle: Bundle) {
    super.onSaveInstanceState(bundle)
    bundle.putString(STATE_SELECTED_MODEL, selectedModel)
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
    val newLensFacing =
      if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
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
    )
      .show()
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
//    if (cameraProvider == null || (exerciseViewModel.isResting.value == true&& exerciseViewModel.restTime.value<3)) {
//      return // 쉬는 시간일 경우, 분석을 실행하지 않음
//    }

    if (cameraProvider != null) {
      // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
      cameraProvider!!.unbindAll()
      bindPreviewUseCase()
      bindAnalysisUseCase()
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
//    if(exerciseViewModel.isResting.value == true && exerciseViewModel.restTime.value<3)
//      return

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
            val exerciseType = exerciseViewModel.exerciseType.value
        if (exerciseType != null) {
          Log.d("운동 종류",exerciseType)
        }
            val poseDetectorOptions = PreferenceUtils.getPoseDetectorOptionsForLivePreview(this)
            val shouldShowInFrameLikelihood =
              PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this)
            val visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this)
            val rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this)
            val runClassification = true
              //PreferenceUtils.shouldPoseDetectionRunClassification(this)
            PoseDetectorProcessor(
              this,
              poseDetectorOptions,
              shouldShowInFrameLikelihood,
              visualizeZ,
              rescaleZ,
              runClassification,
              /* isStreamMode = */ true,
              exerciseType,
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
        //쉬는시간일 때 이미지 처리 패스
//        if(exerciseViewModel.isResting.value == true){
//          imageProxy.close()
//          return@Analyzer
//        }
        //이미지 분석 실행
    //    if(exerciseViewModel.isResting.value==false||exerciseViewModel.restTime.value<3){
          if (needUpdateGraphicOverlayImageSourceInfo) {
            val isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            if (rotationDegrees == 0 || rotationDegrees == 180) {
              graphicOverlay!!.setImageSourceInfo(imageProxy.width, imageProxy.height, isImageFlipped)
            } else {
              graphicOverlay!!.setImageSourceInfo(imageProxy.height, imageProxy.width, isImageFlipped)
            }
            needUpdateGraphicOverlayImageSourceInfo = false
          }
          try {
            imageProcessor!!.processImageProxy(imageProxy, graphicOverlay)
          } catch (e: MlKitException) {
            Log.e(TAG, "Failed to process image. Error: " + e.localizedMessage)
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
          }
        //}

      },
    )
    cameraProvider!!.bindToLifecycle(this, cameraSelector!!, analysisUseCase)
  }


  companion object {
    private const val TAG = "CameraXLivePreview"
   private const val POSE_DETECTION = "Pose Detection"
    private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

    private const val STATE_SELECTED_MODEL = "selected_model"
  }
}

