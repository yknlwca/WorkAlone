package com.ssafy.workalone.presentation.ui.screen

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.viewmodels.video.AWSS3ViewModel
import java.io.File
import java.io.InputStream

@Composable
fun AWSS3UploadScreen(viewModel: AWSS3ViewModel = AWSS3ViewModel()) {
    val context = LocalContext.current

    // `res/raw/sample_video.mp4` 파일의 URI를 가져옵니다.
    val sampleVideoUri = getRawResourceUri(context, R.raw.sample_video)

    val selectedFileUri by remember { mutableStateOf<Uri?>(sampleVideoUri) }
    val preSignedUrl by viewModel.preSignedUrl.collectAsStateWithLifecycle()
    val uploadResponse by viewModel.uploadVideoResponse.collectAsStateWithLifecycle()

    Log.d("test", "$sampleVideoUri")
    Log.d("test", "$selectedFileUri")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.getPreSignedUrl() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pre-Signed URL 받기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        preSignedUrl?.let {
            Text(
                text = "Pre-Signed URL: ${it.preSignedUrl}",
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 샘플 비디오로 설정된 파일 URI 표시
        selectedFileUri?.let { uri ->
            Text(
                text = "Selected Sample File: ${getFileName(context, uri)}",
                style = MaterialTheme.typography.body1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                preSignedUrl?.preSignedUrl?.let { url ->
                    selectedFileUri?.let { uri ->
                        val inputStream = context.contentResolver.openInputStream(uri)
                        if (inputStream != null) {
                            viewModel.uploadVideo(url, inputStreamToFile(inputStream, context))
                        }
                    }
                }
            },
            enabled = preSignedUrl != null && selectedFileUri != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Upload Video")
        }

        Spacer(modifier = Modifier.height(16.dp))

        uploadResponse?.let { success ->
            Text(
                text = if (success) "Upload Successful!" else "Upload Failed!",
                color = if (success) Color.Green else Color.Red,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

// Helper function to get a Uri for a raw resource file
fun getRawResourceUri(context: Context, resourceId: Int): Uri {
    return Uri.parse("android.resource://${context.packageName}/$resourceId")
}

// Helper function to get file name from URI
fun getFileName(context: Context, uri: Uri): String {
    var name = ""
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        }
    }
    return name
}

// Converts InputStream to File in cache directory
fun inputStreamToFile(inputStream: InputStream, context: Context): File {
    val file = File(context.cacheDir, "temp_video_file.mp4")
    file.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return file
}

