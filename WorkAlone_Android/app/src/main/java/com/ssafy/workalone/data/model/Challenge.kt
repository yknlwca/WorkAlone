package com.ssafy.workalone.data.model

import com.google.gson.annotations.SerializedName

data class Challenge(
    @SerializedName("groupId")
    val groupId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("exerciseSet")
    val exerciseSet: Int?,
    @SerializedName("exerciseRepeat")
    val exerciseRepeat: Int?,
    @SerializedName("restBtwSet")
    val restBtwExercise: Int?,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("setType")
    val setType: String,
    @SerializedName("exerciseType")
    val exerciseType: String?
)