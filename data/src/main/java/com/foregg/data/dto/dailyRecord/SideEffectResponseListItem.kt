package com.foregg.data.dto.dailyRecord

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class SideEffectResponseListItem(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("id")
    val id: Long = -1
): DataDto