package com.ssafy.workalone.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.data.model.UploadStatus
import com.ssafy.workalone.presentation.viewmodels.VideoViewModel
import java.io.File

@Composable
fun VideoUploadScreen(viewModel: VideoViewModel = VideoViewModel()) {
    // 저장없이 전송하려면 Intent로 받아서 바로 전송 가능
    // uploadStatus 구독
    val uploadStatus by viewModel.uploadStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uploadStatus) {
            is UploadStatus.Idle -> {
                Text("파일 업로드")
            }

            is UploadStatus.Uploading -> {
                CircularProgressIndicator() // 로딩 중 표시
                Spacer(modifier = Modifier.height(8.dp))
                Text("업로드 중...")
            }

            is UploadStatus.Success -> {
                Text((uploadStatus as UploadStatus.Success).message)
            }

            is UploadStatus.Error -> {
                Text(
                    (uploadStatus as UploadStatus.Error).message,
                    color = MaterialTheme.colors.error
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val videoFile = File("") // 실제 파일
            viewModel.uploadVideo(videoFile)
        }) {
            Text("업로드 시작")
        }
    }
}