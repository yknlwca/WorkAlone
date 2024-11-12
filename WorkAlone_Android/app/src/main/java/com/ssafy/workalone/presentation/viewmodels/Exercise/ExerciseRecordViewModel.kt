package com.ssafy.workalone.presentation.viewmodels.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
<<<<<<<< HEAD:WorkAlone_Android/app/src/main/java/com/ssafy/workalone/presentation/viewmodels/ExerciseSummaryViewModel.kt
import com.ssafy.workalone.data.model.ExerciseSummary
import com.ssafy.workalone.data.repository.ExerciseSummaryRepository
========
import com.ssafy.workalone.data.model.exercise.ExerciseRecord
import com.ssafy.workalone.data.repository.ExerciseRecordRepository
>>>>>>>> origin/develop:WorkAlone_Android/app/src/main/java/com/ssafy/workalone/presentation/viewmodels/Exercise/ExerciseRecordViewModel.kt
import com.ssafy.workalone.global.exception.CustomException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExerciseSummaryViewModel(
    private val exerciseSummaryRepository: ExerciseSummaryRepository = ExerciseSummaryRepository()
): ViewModel() {

    fun getExerciseSummary(date: String): Flow<ExerciseSummary> = flow {
        emitAll(exerciseSummaryRepository.getExerciseSummary(date))
        Log.d("SummaryViewModel", "getExerciseSummary called with date: $date")
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