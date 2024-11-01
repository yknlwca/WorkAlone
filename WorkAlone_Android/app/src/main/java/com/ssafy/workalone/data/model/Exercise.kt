package com.ssafy.workalone.data.model

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("exerciseId")
    val exerciseId: Long,
    @SerializedName("exerciseSet")
    val exerciseSet: Int,
    @SerializedName("setDetail")
    val setDetail: Int,
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
    @SerializedName("exerciseType")
    val exerciseType: String,
    val picture: Int,
    val content: String
)
