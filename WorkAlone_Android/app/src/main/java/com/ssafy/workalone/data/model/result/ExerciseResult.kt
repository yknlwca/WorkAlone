package com.ssafy.workalone.data.model.result

data class ExerciseResult(
    var exerciseId: Long,
    var exerciseType: String,
    var time: String,
)

data class ResultList(
    var result: List<ExerciseResult>,
    var totalTime: String,
    var videoUri: String
)