package com.ssafy.workalone.data.repository

import android.util.Log
import com.ssafy.workalone.data.model.video.PreSignedUrl
import com.ssafy.workalone.data.remote.AWSS3Service
import com.ssafy.workalone.data.remote.RetrofitFactory
import okhttp3.RequestBody

class AWSS3Repository(
    private val videoService: AWSS3Service = RetrofitFactory.getInstance()
        .create(AWSS3Service::class.java)
) {
    suspend fun getPreSignedUrl(): PreSignedUrl? {
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