package com.ssafy.workalone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.workalone.data.model.Exercise

@Database(
    entities = [Exercise::class],
    version = 2,
    exportSchema = false
)

abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}