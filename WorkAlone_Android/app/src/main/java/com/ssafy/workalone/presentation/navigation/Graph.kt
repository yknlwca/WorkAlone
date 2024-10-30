package com.ssafy.workalone.presentation.navigation

import android.content.Context
import androidx.room.Room
import com.ssafy.workalone.data.local.ExerciseDatabase
import com.ssafy.workalone.data.local.ExerciseRecordDatabase
import com.ssafy.workalone.data.repository.ExerciseRecordRepository
import com.ssafy.workalone.data.repository.ExerciseRepository

object Graph {

    lateinit var database: ExerciseDatabase

    val exerciseRepository by lazy {
        ExerciseRepository(exerciseDao = database.exerciseDao())
    }

    fun provide(context: Context) {
        database =
            Room.databaseBuilder(context, ExerciseDatabase::class.java, "exercise.db").build()
    }
}