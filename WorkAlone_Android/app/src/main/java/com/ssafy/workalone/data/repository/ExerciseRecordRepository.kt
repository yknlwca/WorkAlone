package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.model.ExerciseRecord
import com.ssafy.workalone.data.remote.ExerciseService
import com.ssafy.workalone.data.remote.RetrofitFactory
import com.ssafy.workalone.global.exception.handleApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExerciseRecordRepository(
    private val exerciseService: ExerciseService =
        RetrofitFactory.getInstance().create(ExerciseService::class.java)
) {
    fun getExersiceRecords(date: String): Flow<ExerciseRecord> = flow {
        val response = exerciseService.getExerciseRecords(date)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) }
        } else {
            handleApiError(response.code(), response.errorBody()?.string())
        }
    }.flowOn(Dispatchers.IO)
}