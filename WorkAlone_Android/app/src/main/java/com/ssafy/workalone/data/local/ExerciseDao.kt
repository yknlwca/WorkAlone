package com.ssafy.workalone.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.workalone.data.model.Exercise
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addExercise(exercise: Exercise)

    @Query("SELECT * FROM `exercise-table`")
    abstract fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM `exercise-table` WHERE id = :id")
    abstract fun getExerciseById(id: Long): Flow<Exercise>
}