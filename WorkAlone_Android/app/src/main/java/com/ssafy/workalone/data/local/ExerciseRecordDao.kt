package com.ssafy.workalone.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.workalone.data.model.Exercise
import com.ssafy.workalone.data.model.ExerciseRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRecords(exerciseRecords: List<ExerciseRecord>)

    @Query("SELECT * FROM `exercise-record`")
    fun getAllExerciseRecords(): Flow<List<ExerciseRecord>>
}