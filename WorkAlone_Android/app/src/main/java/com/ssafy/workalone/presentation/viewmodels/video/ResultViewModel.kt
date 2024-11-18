package com.ssafy.workalone.presentation.viewmodels.video

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.workalone.data.model.result.AwsUrl
import com.ssafy.workalone.data.model.result.ResultList
import com.ssafy.workalone.data.model.result.Summary
import com.ssafy.workalone.data.model.result.SummaryList
import com.ssafy.workalone.data.repository.ResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.math.roundToInt

class ResultViewModel(private val resultRepository: ResultRepository = ResultRepository()) :
    ViewModel() {

    private val _awsUrl = MutableStateFlow<AwsUrl?>(null)
    val awsUrl: StateFlow<AwsUrl?> = _awsUrl

    private val _preSigned = MutableStateFlow<String?>(null)
    val preSigned: StateFlow<String?> = _preSigned

    private val _objectUrl = MutableStateFlow<String?>(null)
    val objectUrl: StateFlow<String?> = _objectUrl

    private val _uploadVideoResponse = MutableStateFlow<Boolean?>(null)
    val uploadVideoResponse: StateFlow<Boolean?> = _uploadVideoResponse

    private val _resultList = MutableStateFlow<ResultList?>(null)
    val resultList: StateFlow<ResultList?> = _resultList

    fun getAwsUrlRequest() {
        viewModelScope.launch {
            _awsUrl.update { resultRepository.getPreSignedUrl() }
            _preSigned.update { _awsUrl.value?.preSignedUrl }
            _objectUrl.value = _awsUrl.value?.objectUrl
            Log.d("ViewModel Check", "${preSigned.value}")
        }
    }

    fun uploadVideo(preSignedUrl: String, file: File) {
        viewModelScope.launch {
            try {
                Log.d("Upload", "업로드중...")
                val requestBody = file.asRequestBody("video/mp4".toMediaTypeOrNull())
                val uploadResult = resultRepository.uploadToS3(preSignedUrl, requestBody)
                _uploadVideoResponse.value = uploadResult
            } catch (e: Exception) {
                Log.e("Upload", "실패", e)
                _uploadVideoResponse.value = false
            }
        }
    }

    fun sendExerciseResult(resultList: ResultList, weight: Int) {
        viewModelScope.launch {
            val summaryList = convertToSummaryList(resultList, weight)
            resultRepository.sendExerciseResult(summaryList)
        }
    }

    private fun convertToSummaryList(resultList: ResultList, weight: Int): SummaryList {
        val summaryList = resultList.result.map { exerciseResult ->
            val time = exerciseResult.time
            val duration = convertTimeToSeconds(time)
            val kcal = when (exerciseResult.exerciseType) {
                "스쿼트" -> (6.0 * weight * (duration / 3600.0)).roundToInt()
                "푸쉬업" -> (4.0 * weight * (duration / 3600.0)).roundToInt()
                "윗몸 일으키기" -> (4.0 * weight * (duration / 3600.0)).roundToInt()
                "플랭크" -> (3.0 * weight * (duration / 3600.0)).roundToInt()
                else -> 0
            }
            Summary(
                exerciseId = exerciseResult.exerciseId,
                time = time,
                kcal = kcal
            )
        }
        return SummaryList(summaryList = summaryList, videoUrl = _objectUrl.value.toString())
    }

    fun convertTimeToSeconds(time: String): Int {
        val timeParts = time.split(":")
        val minutes = timeParts[0].toInt()
        val seconds = timeParts[1].toInt()
        return (minutes * 60) + seconds
    }
}