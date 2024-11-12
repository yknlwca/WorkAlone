package com.ssafy.workalone.data.model.exercise

data class ExerciseData(
    val exerciseId:Long,
    val seq:Int,
    val title: String,
    val restBtwSet: Int,
    val exerciseSet: Int,
    val exerciseRepeat: Int,
    val type: String
)