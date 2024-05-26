package com.foregg.data.dto.dailyRecord

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class DailyRecordResponse(
    @SerializedName("dailyResponseDTO")
    val dailyResponseDTO: List<DailyRecordResponseItem> = emptyList()
): DataDto
