package com.ssafy.workalone.data.model

import com.google.gson.annotations.SerializedName


data class ExerciseSummary(
    @SerializedName("totalTime")
    val totalDuration: Int,
    @SerializedName("totalKcal")
    val totalKcal: Int,
    @SerializedName("totalSummary")
    val totalSummary: List<List<ExerciseDetail>>

)

//data class ExerciseTime(
//    @SerializedName("hour")
//    val hours: Int,
//    @SerializedName("minutes")
//    val minutes: Int,
//    @SerializedName("seconds")
//    val seconds: Int,
//)

data class ExerciseDetail(
    @SerializedName("groupId")
    val groupId: Int,
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("exerciseType")
    val exerciseType: String,
    @SerializedName("exerciseSet")
    val exerciseSet: Int,
    @SerializedName("exerciseRepeat")
    val exerciseRepeat: Int,
    @SerializedName("time")
    val exerciseDuration: Int,
    @SerializedName("kcal")
    val kcal: Int,
    @SerializedName("videoUrl")
    val videoUrl: String
)

