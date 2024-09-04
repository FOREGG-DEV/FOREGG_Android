package com.hugg.data.dto.dailyRecord

import com.hugg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class InjectionInfoResponse(
    @SerializedName("name")
    val name : String = "",
    @SerializedName("description")
    val description : String?  = "",
    @SerializedName("image")
    val image : String? = "",
    @SerializedName("time")
    val time : String = "",
) : DataDto
