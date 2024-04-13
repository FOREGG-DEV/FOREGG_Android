package com.foregg.data.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<Vo>(
    @SerializedName("data")
    val data : Vo? = null,
    @SerializedName("statusCode")
    val statusCode : Int = -1
)
