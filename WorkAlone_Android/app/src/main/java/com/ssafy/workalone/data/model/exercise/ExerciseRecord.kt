package com.ssafy.workalone.data.model.exercise

import com.google.gson.annotations.SerializedName

//data class ExerciseRecord(
//    @SerializedName("exerciseId")
//    val id: Long,
//    @SerializedName("title")
//    val title: String,
//    @SerializedName("exerciseCount")
//    val exerciseCount: String,
//    @SerializedName("exerciseDuration")
//    val exerciseDuration: Int,
//    @SerializedName("calorie")
//    val calorie: Int
//)
data class ExerciseRecord(
    @SerializedName("totalTime")
    val totalTime: ExerciseTime,
    val totalKcal: Int,
    @SerializedName("totalSummary")
    val totalSummary: List<List<ExerciseDetail>>

)

data class ExerciseTime(
    @SerializedName("hour")
    val hours: Int,
    @SerializedName("minutes")
    val minutes: Int,
    @SerializedName("seconds")
    val seconds: Int,
)

data class ExerciseDetail(
    @SerializedName("groupId")
    val groupId: Int,
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("exerciseType")
    val exerciseType: String,
    @SerializedName("time")
    val exerciseTime: ExerciseTime,
    @SerializedName("kcal")
    val kcal: Int,
)

