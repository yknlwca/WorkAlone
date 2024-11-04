package com.ssafy.workalone.data.model

import com.google.gson.annotations.SerializedName

data class ExerciseRecord(
    @SerializedName("exerciseId")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("exerciseCount")
    val exerciseCount: String,
    @SerializedName("exerciseDuration")
    val exerciseDuration: Int,
    @SerializedName("calorie")
    val calorie: Int
)