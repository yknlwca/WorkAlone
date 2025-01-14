package com.ssafy.workalone.data.model.exercise

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("exerciseId")
    val exerciseId: Long,
    @SerializedName("seq")
    val seq: Int,
    @SerializedName("exerciseSet")
    val exerciseSet: Int,
    @SerializedName("exerciseRepeat")
    val exerciseRepeat: Int,
    @SerializedName("restBtwSet")
    val restBtwSet: Int,
    @SerializedName("setType")
    val setType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("subTitle")
    val subTitle: String,
    @SerializedName("basicPose")
    val basicPose: String,
    @SerializedName("movement")
    val movement: String,
    @SerializedName("breath")
    val breath: String,

    val picture: Int?,
    val content: String?
)
