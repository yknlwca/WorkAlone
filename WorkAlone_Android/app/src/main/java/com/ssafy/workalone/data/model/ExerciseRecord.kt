package com.ssafy.workalone.data.model

data class ExerciseRecord(
    val id: Long = 0L,
    val title: String,
    val exerciseCount: String,
    val exerciseDuration: Int,
    val calorie: Int
)