package com.ssafy.workalone.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise-record")
data class ExerciseRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "exercise-count")
    val exerciseCount: String,
    @ColumnInfo(name = "exercise-duration")
    val exerciseDuration: Int,
    @ColumnInfo(name = "calorie")
    val calorie: Int
)