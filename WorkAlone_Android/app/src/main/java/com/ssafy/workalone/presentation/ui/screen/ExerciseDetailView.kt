package com.ssafy.workalone.presentation.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ssafy.workalone.mlkit.kotlin.LivePreviewActivity
import com.ssafy.workalone.presentation.navigation.Screen
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel


@Composable
fun ExerciseDetailView(
    navController: NavController,
    viewModel: ExerciseViewModel,
    id: Long, exerciseType: String
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scaffoldState = rememberScaffoldState()
    val exercises = viewModel.getExerciseById(id, exerciseType)
        .collectAsState(initial = listOf())
    var currentIndex by remember { mutableStateOf(0) }
    var currentExercise = exercises.value.getOrNull(currentIndex)
    val scrollState = rememberScrollState()
    // 카메라 실행 런처
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 촬영 완료
            navController.navigate(Screen.IndividualComplete.route)
            Toast.makeText(context, "촬영되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    // 권한 요청 런처
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // 권한 허용이면 카메라 실행
                startCamera(activity, takePictureLauncher)
            } else {
                Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_LONG).show()
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

//                        // YouTube 영상 -> 나중에 직접 찍은 동영상으로 바꿔야함
//                        getYoutubeVideoId("https://www.youtube.com/watch?v=50f62PSGY7k&pp=ygUJ7Iqk7L-87Yq4")?.let { it1 ->
//                            YouTubePlayerScreen(
//                                videoId = it1
//                            )
//                        }

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
                                }
                            )
                        } else {
                            CustomButton(
                                text = "운동 시작하기",
                                onClick = {
                                    val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                                        context,
                                        android.Manifest.permission.CAMERA
                                    )
                                    if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                                    } else {
                                        val intent = Intent(context, LivePreviewActivity::class.java)
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

private fun getYoutubeVideoId(url: String?): String? {
    if (url.isNullOrEmpty()) return null
    val regex = "v=([^&]+)".toRegex()
    return regex.find(url)?.groupValues?.get(1)
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

// 카메라 실행 함수
@SuppressLint("QueryPermissionsNeeded")
private fun startCamera(activity: Activity?, takePictureLauncher: ActivityResultLauncher<Intent>) {
    activity?.let {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(it.packageManager)?.also {
            Log.d("Camera", "Camera Intent OK")
            takePictureLauncher.launch(takePictureIntent)
        }
    }
}
