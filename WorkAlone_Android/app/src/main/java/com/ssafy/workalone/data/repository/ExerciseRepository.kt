package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.remote.ExerciseService
import com.ssafy.workalone.data.remote.RetrofitFactory
import com.ssafy.workalone.global.exception.handleApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExerciseRepository(
    private val exerciseService: ExerciseService =
        RetrofitFactory.getInstance().create(ExerciseService::class.java)
) {

    fun getExercises(): Flow<List<Exercise>> = flow {
        val response = exerciseService.getExercises()
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            handleApiError(response.code(), response.errorBody()?.string())
        }
    }

    fun getExerciseByIdAndType(exerciseId: Long, exerciseType: String): Flow<List<Exercise>> =
        flow {
            val response = exerciseService.getExercise(exerciseId, exerciseType)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                handleApiError(response.code(), response.errorBody()?.string())
            }
        }
}