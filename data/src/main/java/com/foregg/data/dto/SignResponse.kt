package com.foregg.data.dto

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class SignResponse(
    @SerializedName("keycode")
    val keycode : String? ="",
    @SerializedName("accessToken")
    val accessToken : String? ="",
    @SerializedName("spouseCode")
    val spouseCode : String? ="",
) : DataDto
