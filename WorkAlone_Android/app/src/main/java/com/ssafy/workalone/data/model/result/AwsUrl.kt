package com.ssafy.workalone.data.model.result

import com.google.gson.annotations.SerializedName

data class AwsUrl(
    @SerializedName("presignedUrl")
    val preSignedUrl: String = "",

    @SerializedName("objectUrl")
    val objectUrl: String = ""
)