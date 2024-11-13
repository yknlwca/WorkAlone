package com.ssafy.workalone.presentation.viewmodels.video

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.video.AwsUrl
import com.ssafy.workalone.data.repository.AWSS3Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AWSS3ViewModel(private val awsS3Repository: AWSS3Repository = AWSS3Repository()) :
    ViewModel() {

    private val _awsUrl = MutableStateFlow<AwsUrl?>(null)
    val awsUrl: StateFlow<AwsUrl?> = _awsUrl

    private val _preSigned = MutableStateFlow<String?>(null)
    val preSigned: StateFlow<String?> = _preSigned

    private val _objectUrl = MutableStateFlow<String?>(null)
    val objectUrl: StateFlow<String?> = _objectUrl

    private val _uploadVideoResponse = MutableStateFlow<Boolean?>(null)
    val uploadVideoResponse: StateFlow<Boolean?> = _uploadVideoResponse

    fun getAwsUrlRequest() {
        viewModelScope.launch {
            _awsUrl.update { awsS3Repository.getPreSignedUrl() }
            _preSigned.update { _awsUrl.value?.preSignedUrl }
            _objectUrl.value = _awsUrl.value?.objectUrl
            Log.d("ViewModel Check", "${preSigned.value}")
        }
    }

    fun uploadVideo(preSignedUrl: String, file: File) {
        viewModelScope.launch {
            try {
                Log.d("Upload", "업로드중...")
                val requestBody = file.asRequestBody("video/mp4".toMediaTypeOrNull())
                val uploadResult = awsS3Repository.uploadToS3(preSignedUrl, requestBody)
                _uploadVideoResponse.value = uploadResult
                Log.d("Upload", "Upload result: $uploadResult")
            } catch (e: Exception) {
                Log.e("Upload", "실패", e)
                _uploadVideoResponse.value = false
            }
        }
    }
}