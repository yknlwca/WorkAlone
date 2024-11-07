package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.remote.RetrofitFactory
import com.ssafy.workalone.data.remote.VideoService
import com.ssafy.workalone.global.exception.handleApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class VideoRepository(
    private val videoService: VideoService =
        RetrofitFactory.getInstance().create(VideoService::class.java)
) {
    suspend fun uploadVideo(videoFile: File): Flow<Boolean> = flow {
        // 파일 압축
        val compressedData = compressVideoFile(videoFile)

        // MultipartBody 생성
        val requestBody = RequestBody.create("application/zip".toMediaTypeOrNull(), compressedData)
        val multipartBody = MultipartBody.Part.createFormData("file", "${videoFile.name}.zip", requestBody)

        // 파일 업로드
        val response = videoService.uploadVideo(multipartBody)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            handleApiError(response.code(), response.errorBody()?.string())
        }
    }

    private fun compressVideoFile(videoFile: File): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ZipOutputStream(outputStream).use { zipOut ->
            FileInputStream(videoFile).use { fis ->
                val zipEntry = ZipEntry(videoFile.name)
                zipOut.putNextEntry(zipEntry)
                fis.copyTo(zipOut)
            }
        }
        return outputStream.toByteArray()
    }
}