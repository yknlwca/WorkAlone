package com.ssafy.workalone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.UploadStatus
import com.ssafy.workalone.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

class VideoViewModel(private val videoRepository: VideoRepository = VideoRepository()) :
    ViewModel() {

    // 업로드 상태를 나타내는 StateFlow
    private val _uploadStatus = MutableStateFlow<UploadStatus>(UploadStatus.Idle)
    val uploadStatus: StateFlow<UploadStatus> = _uploadStatus

    // 비디오 업로드 함수
    fun uploadVideo(videoFile: File) {
        viewModelScope.launch {
            videoRepository.uploadVideo(videoFile)
                .catch { e ->
                    // 예외 발생 시 실패 상태로 설정
                    _uploadStatus.value = UploadStatus.Error("Error: ${e.message}")
                }
                .collect { success ->
                    // 성공 여부에 따라 상태 설정
                    _uploadStatus.value = if (success) {
                        UploadStatus.Success("파일 업로드 성공")
                    } else {
                        UploadStatus.Error("파일 업로드 실패")
                    }
                }
        }
    }
}
