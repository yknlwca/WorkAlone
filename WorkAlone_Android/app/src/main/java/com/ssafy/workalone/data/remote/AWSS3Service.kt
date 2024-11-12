package com.ssafy.workalone.data.remote

import com.ssafy.workalone.data.model.video.PreSignedUrl
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Url

interface AWSS3Service {
    @PUT
    suspend fun uploadVideo(
        @Url preSignedUrl: String,
        @Body file: RequestBody
    ): Response<Void>

    @GET("/summary/url")
    suspend fun getPreSignedUrl(): Response<PreSignedUrl>
}
