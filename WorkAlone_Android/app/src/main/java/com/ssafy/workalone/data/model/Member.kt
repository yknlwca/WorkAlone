package com.ssafy.workalone.data.model

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("memberId")
    val memberId: Long = 1L,

    @SerializedName("memberName")
    val memberName: String = "김현태",

    @SerializedName("memberWeight")
    val memberWeight: Float = 60.0f,
)