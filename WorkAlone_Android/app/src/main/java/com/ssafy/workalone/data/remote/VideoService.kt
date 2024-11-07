package com.ssafy.workalone.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VideoService {
    @Multipart
    @POST("upload")
    suspend fun uploadVideo(@Part file: MultipartBody.Part): Response<Boolean>
}