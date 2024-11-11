package com.ssafy.workalone.data.model.video

import com.google.gson.annotations.SerializedName

data class PreSignedUrl(
    @SerializedName("presignedUrl")
    val preSignedUrl: String = "",

    @SerializedName("objectUrl")
    val objectUrl: String = ""
)