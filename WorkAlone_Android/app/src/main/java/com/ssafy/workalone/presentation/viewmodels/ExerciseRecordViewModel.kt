package com.ssafy.workalone.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.workalone.data.model.ExerciseRecord
import com.ssafy.workalone.data.repository.ExerciseRecordRepository
import com.ssafy.workalone.global.exception.CustomException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRecordViewModel(private val exerciseRecordRepository: ExerciseRecordRepository): ViewModel() {

    val getExerciseRecords: Flow<List<ExerciseRecord>> = flow {
        emitAll(exerciseRecordRepository.getExersiceRecords())
    }.flowOn(Dispatchers.IO).catch { e -> handleException(e) }

    private fun handleException(exception: Throwable) {
        when (exception) {
            is CustomException.NetworkException -> {
                // 네트워크 처리 문제
                Log.d("Network ViewModel", "Network error : ${exception.message}")
            }

            is CustomException.ServerException -> {
                // 서버 에러 처리
                Log.d("Server ViewModel", "Server error : ${exception.message}")
            }

            is CustomException.ClientException -> {
                // 안드로이드 에러 처리
                Log.d("Client ViewModel", "Client error : ${exception.message}")
            }

            is CustomException.UnknownException -> {
                // 알 수 없는 에러 찾아 보기
                Log.d("Unknown ViewModel", "Unknown error : ${exception.message}")
            }
        }
    }
}