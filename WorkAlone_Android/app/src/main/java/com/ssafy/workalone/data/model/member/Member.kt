package com.ssafy.workalone.data.model.member

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id")
    val memberId: Long,

    @SerializedName("name")
    val memberName: String,

    @SerializedName("nickname")
    val memberNickname: String,

    @SerializedName("email")
    val memberEmail: String,

    @SerializedName("phone_number")
    val memberPhoneNumber: String,

    @SerializedName("gender")
    val memberGender: String,

    @SerializedName("is_trainer")
    val isTrainer: Boolean,

    @SerializedName("height")
    val memberHeight: Int,

    @SerializedName("weight")
    val memberWeight: Int,

    @SerializedName("is_recording")
    val isRecording: Boolean
)


data class SaveMember(
    @SerializedName("name")
    val memberName: String,

    @SerializedName("weight")
    val memberWeight: Int
)

data class SaveRecording(
    @SerializedName("memberId")
    val memberId: Long,
    @SerializedName("isRecording")
    val isRecording: Boolean
)