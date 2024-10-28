package com.ssafy.workalone.data.repository

import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.local.ExerciseDao
import com.ssafy.workalone.data.model.DummyExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun getExercises(): Flow<List<Exercise>> = exerciseDao.getAllExercises()

    fun getExerciseById(id: Long): Flow<Exercise> {
        return exerciseDao.getExerciseById(id)
    }

    suspend fun addDummyData() {
        if (exerciseDao.getAllExercises().firstOrNull().isNullOrEmpty()) {
            DummyExercise.exerciseList.forEach { exercise ->
                exerciseDao.addExercise(exercise)
            }
        }
    }
}