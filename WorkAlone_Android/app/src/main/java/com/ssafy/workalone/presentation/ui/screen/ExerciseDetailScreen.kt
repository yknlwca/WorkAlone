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
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.presentation.ui.component.AppBarView
import com.ssafy.workalone.presentation.ui.component.CustomButton
import com.ssafy.workalone.presentation.ui.component.YouTubePlayer
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme
import com.ssafy.workalone.presentation.viewmodels.ExerciseViewModel


@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    viewModel: ExerciseViewModel,
    id: Long
) {
    val context = LocalContext.current
    val activity = context as? Activity
    // 카메라 실행 런처
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
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
    val scaffoldState = rememberScaffoldState()
    val exercise = viewModel.getExerciseById(id)
        .collectAsState(initial = Exercise(0L, "", "", "", "", "", "", 0))
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            AppBarView(
                title = exercise.value.title,
                onBackNavClicked = { navController.navigateUp() }
            )
        },
        bottomBar = {
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
                        startCamera(activity, takePictureLauncher)
                    }
                }
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
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
                    text = exercise.value.title,
                    style = WorkAloneTheme.typography.Heading02,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = exercise.value.subTitle,
                    style = WorkAloneTheme.typography.Heading04,
                    color = WalkOneBlue500,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // YouTube 영상
                getYoutubeVideoId(exercise.value.content)?.let { it1 ->
                    YouTubePlayer(
                        youtubeVideoId = it1,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }

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
                SectionItem(title = "기본 자세", description = exercise.value.basicPose)
                Spacer(modifier = Modifier.height(16.dp))
                SectionItem(title = "동작 수행", description = exercise.value.movement)
                Spacer(modifier = Modifier.height(16.dp))
                SectionItem(title = "호흡", description = exercise.value.breath)
            }
        }
    }
}

private fun getYoutubeVideoId(url: String): String? {
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
private fun startCamera(activity: Activity?, takePictureLauncher: ActivityResultLauncher<Intent>) {
    activity?.let {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(it.packageManager)?.also {
            Log.d("Camera", "Camera Intent OK")
            takePictureLauncher.launch(takePictureIntent)
        }
    }
}