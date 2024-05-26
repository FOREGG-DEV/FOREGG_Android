package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.DailyConditionType
import com.google.gson.annotations.SerializedName

data class CreateDailyRecordRequestVo(
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType = DailyConditionType.SOSO,
    @SerializedName("content")
    val content: String = ""
): RequestVo
