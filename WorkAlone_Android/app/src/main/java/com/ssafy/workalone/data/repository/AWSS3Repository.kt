package com.ssafy.workalone.data.repository

import android.util.Log
import com.ssafy.workalone.data.model.video.AwsUrl
import com.ssafy.workalone.data.remote.AWSS3Service
import com.ssafy.workalone.data.remote.RetrofitFactory
import okhttp3.RequestBody

class AWSS3Repository(
    private val videoService: AWSS3Service = RetrofitFactory.getInstance()
        .create(AWSS3Service::class.java)
) {
    // PreSignedUrl과 ObjectUrl을 받아 SharedPreference에 저장
    // PreSignedUrl은 파일과 함께 업로드 요청으로
    // ObjectUrl은 운동 기록과 함께 요청
    suspend fun getPreSignedUrl(): AwsUrl? {
        return try {
            val response = videoService.getPreSignedUrl()
            if (response.isSuccessful) {
                Log.d("AWSS3Repository", "프리사인 URL 가져오기 성공: ${response.body()}")
                response.body()
            } else {
                Log.e("AWSS3Repository", "프리사인 URL 가져오기 실패: ${response.errorBody()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AWSS3Repository", "오류 발생: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun uploadToS3(
        preSignedUrl: String,
        file: RequestBody
    ): Boolean {
        return try {
            val response = videoService.uploadVideo(preSignedUrl, file)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}