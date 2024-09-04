package com.hugg.data.dto.schedule

import com.hugg.domain.model.vo.ScheduleDetailVo
import com.google.gson.annotations.SerializedName

data class ScheduleListResponse(
    @SerializedName("records")
    val records : List<ScheduleDetailVo> = emptyList()
)