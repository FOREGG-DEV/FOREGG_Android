package com.foregg.data.dto

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class ForeggJwtResponse(
    @SerializedName("accessToken")
    val accessToken : String = "",

    @SerializedName("refreshToken")
    val refreshToken : String = ""
): DataDto