package com.ssafy.workalone.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssafy.workalone.data.model.ExerciseRecord

@Database(entities = [ExerciseRecord::class], version = 1)
abstract class ExerciseRecordDatabase(): RoomDatabase() {
    abstract fun exerciseRecordDao(): ExerciseRecordDao
}