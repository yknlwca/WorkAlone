package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.local.ExerciseRecordDao
import com.ssafy.workalone.data.model.ExerciseRecord
import kotlinx.coroutines.flow.Flow

class ExerciseRecordRepository(private val exerciseRecordDao: ExerciseRecordDao) {
    fun getAllExerciseRecords(): Flow<List<ExerciseRecord>> = exerciseRecordDao.getAllExerciseRecords()

    suspend fun insertExerciseRecords(exerciseRecords: List<ExerciseRecord>) = exerciseRecordDao.insertExerciseRecords(exerciseRecords)
}