package com.ssafy.workalone.data.repository

import android.util.Log
import com.ssafy.workalone.data.model.exercise.ExerciseSummary
import com.ssafy.workalone.data.remote.ExerciseService
import com.ssafy.workalone.data.remote.RetrofitFactory
import com.ssafy.workalone.global.exception.handleApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExerciseSummaryRepository(
    private val exerciseService: ExerciseService =
        RetrofitFactory.getInstance().create(ExerciseService::class.java)
) {
    fun getExerciseSummary(date: String): Flow<ExerciseSummary> = flow {
        val response = exerciseService.getExerciseSummary(date)
        Log.d("SummaryRepository", "response: $response")
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            handleApiError(response.code(), response.errorBody()?.string())
        }
    }.flowOn(Dispatchers.IO)
}