package com.foregg.data.dto.schedule

import com.foregg.domain.model.vo.ScheduleDetailVo
import com.google.gson.annotations.SerializedName

data class ScheduleListResponse(
    @SerializedName("records")
    val records : List<ScheduleDetailVo> = emptyList()
)