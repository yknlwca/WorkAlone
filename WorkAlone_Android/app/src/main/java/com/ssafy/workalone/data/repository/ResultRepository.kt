package com.ssafy.workalone.data.repository

import android.util.Log
import com.ssafy.workalone.data.model.result.AwsUrl
import com.ssafy.workalone.data.model.result.SummaryList
import com.ssafy.workalone.data.remote.ResultService
import com.ssafy.workalone.data.remote.RetrofitFactory
import okhttp3.RequestBody

class ResultRepository(
    private val videoService: ResultService = RetrofitFactory.getInstance()
        .create(ResultService::class.java)
) {

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

    // 결과 보내기
    suspend fun sendExerciseResult(summaryList: SummaryList): Boolean {
        return try {
            val response = videoService.sendExerciseResult(summaryList)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}