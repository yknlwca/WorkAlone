package com.ssafy.workalone.data.model.result

data class ExerciseResult(
    val exerciseId: Long,
    val kcal: Int,
    val time: String,
)

data class ResultList(
    val result: List<ExerciseResult>,
    val totalKcal: Int,
    val totalTime: String,
    val videoUri: String
)