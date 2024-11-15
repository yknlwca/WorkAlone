package com.ssafy.workalone.data.model.result

import com.google.gson.annotations.SerializedName

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

data class Summary(
    @SerializedName("exercise_id")
    val exerciseId: Long,
    @SerializedName("time")
    val time: String,
    @SerializedName("kcal")
    val kcal: Int,
)

data class SummaryList(
    @SerializedName("summaryList")
    val summaryList: List<Summary>,
    @SerializedName("video_url")
    val videoUrl: String,
)