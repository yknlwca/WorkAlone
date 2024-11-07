package com.ssafy.workalone.data.model

// 업로드 상태를 나타내는 sealed class
sealed class UploadStatus {
    object Idle : UploadStatus()                  // 초기 상태
    object Uploading : UploadStatus()             // 업로드 진행 중 상태
    data class Success(val message: String) : UploadStatus() // 업로드 성공
    data class Error(val message: String) : UploadStatus()   // 업로드 실패 시 오류 메시지 포함
}