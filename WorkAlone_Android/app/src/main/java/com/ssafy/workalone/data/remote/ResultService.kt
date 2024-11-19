package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.result.AwsUrl
import com.ssafy.workalone.data.model.result.SummaryList
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface ResultService {
    @PUT
    suspend fun uploadVideo(
        @Url preSignedUrl: String,
        @Body file: RequestBody
    ): Response<Void>

    @GET("/summary/url")
    suspend fun getPreSignedUrl(): Response<AwsUrl>

    @POST("/summary")
    suspend fun sendExerciseResult(
        @Body summaryList: SummaryList
    ): Response<Void>
}
