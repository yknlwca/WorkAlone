package com.ssafy.workalone.presentation.viewmodels.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.video.PreSignedUrl
import com.ssafy.workalone.data.repository.AWSS3Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AWSS3ViewModel(private val awsS3Repository: AWSS3Repository = AWSS3Repository()) :
    ViewModel() {

    private val _preSignedUrl = MutableStateFlow<PreSignedUrl?>(null)
    val preSignedUrl: StateFlow<PreSignedUrl?>
        get() = _preSignedUrl

    private val _uploadVideoResponse = MutableStateFlow<Boolean?>(null)
    val uploadVideoResponse: StateFlow<Boolean?>
        get() = _uploadVideoResponse

    fun getPreSignedUrl() {
        viewModelScope.launch {
            _preSignedUrl.value = awsS3Repository.getPreSignedUrl()
        }
    }

    fun uploadVideo(preSignedUrl: String, file: File) {
        viewModelScope.launch {
            val requestBody = file.asRequestBody("video/mp4".toMediaTypeOrNull())
            _uploadVideoResponse.value = awsS3Repository.uploadToS3(preSignedUrl, requestBody)
        }
    }
}