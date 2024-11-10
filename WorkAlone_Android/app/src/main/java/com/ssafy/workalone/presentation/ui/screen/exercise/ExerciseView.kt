package com.ssafy.workalone.presentation.ui.screen.exercise

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ssafy.workalone.data.local.ExerciseInfoPreferenceManager
import com.ssafy.workalone.mlkit.java.CameraXLivePreviewActivity
import com.ssafy.workalone.presentation.ui.component.bottombar.CustomButton
import com.ssafy.workalone.presentation.ui.component.exercise.VideoPlayer
import com.ssafy.workalone.presentation.ui.component.topbar.AppBarView
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.exercise.ExerciseViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseView(
    navController: NavController,
    viewModel: ExerciseViewModel = ExerciseViewModel(context = LocalContext.current),
    id: Long
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val exercises = viewModel.getExerciseById(id).collectAsState(initial = listOf())
    var currentIndex by remember { mutableStateOf(0) }
    var currentExercise = exercises.value.getOrNull(currentIndex)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val preferenceManager = ExerciseInfoPreferenceManager(context)
    val intent = Intent(context, CameraXLivePreviewActivity::class.java)

    val cameraPermissionCheck = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.CAMERA
    )
    val audioPermissionCheck = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.RECORD_AUDIO
    )

    // 권한 요청 런처
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[android.Manifest.permission.CAMERA] ?: false
            val audioGranted = permissions[android.Manifest.permission.RECORD_AUDIO] ?: false
            if (cameraGranted) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_LONG).show()
            }
            if (!audioGranted) {
                Toast.makeText(context, "음성 기능은 오디오 권한이 필요합니다.", Toast.LENGTH_LONG).show()
            }
        }

    WorkAloneTheme {
        val typography = LocalWorkAloneTypography.current
        Scaffold(
            topBar = {
                AppBarView(
                    title = currentExercise?.title ?: "Loading...",
                    navController = navController
                )
            },
            scaffoldState = scaffoldState,
        ) { paddingValue ->
            currentExercise?.let { exerciseData ->
                Column(
                    modifier = Modifier
                        .padding(paddingValue)
                        .fillMaxSize()
                        .background(WalkOneGray300),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(WalkOneGray50)
                            .padding(30.dp),
                    ) {
                        // 제목 및 부제목
                        Text(
                            text = exerciseData.title,
                            style = typography.Heading02,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = exerciseData.subTitle,
                            style = typography.Heading04,
                            color = WalkOneBlue500,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        VideoPlayer(
                            videoUrl = "android.resource://com.ssafy.workalone/raw/sample_video",
                            viewModel = viewModel
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(WalkOneGray50)
                            .padding(30.dp)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        // 운동 방법
                        Text(
                            text = "운동 방법",
                            style = WorkAloneTheme.typography.Heading03,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        SectionItem(title = "기본 자세", description = exerciseData.basicPose)
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionItem(title = "동작 수행", description = exerciseData.movement)
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionItem(title = "호흡", description = exerciseData.breath)
                        Spacer(modifier = Modifier.height(32.dp))
                        if (currentIndex < exercises.value.size - 1) {
                            CustomButton(
                                text = "다음 운동 보기",
                                onClick = {
                                    currentIndex += 1
                                    coroutineScope.launch {
                                        scrollState.animateScrollTo(0)
                                    }
                                }
                            )
                        } else {
                            CustomButton(
                                text = "운동 시작하기",
                                onClick = {
                                    viewModel.saveExercisesPreferences(exercises.value)
                                    Log.d("exercise test",
                                        preferenceManager.getExerciseList().toString()
                                    )
                                    if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED || audioPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissionLauncher.launch(
                                            arrayOf(
                                                android.Manifest.permission.CAMERA,
                                                android.Manifest.permission.RECORD_AUDIO
                                            )
                                        )
                                    } else {
                                        context.startActivity(intent)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionItem(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = WorkAloneTheme.typography.Heading04,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = description,
            style = WorkAloneTheme.typography.Body01,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
