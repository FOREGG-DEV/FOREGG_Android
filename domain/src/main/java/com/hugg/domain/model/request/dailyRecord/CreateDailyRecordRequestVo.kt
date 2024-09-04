package com.hugg.domain.model.request.dailyRecord

import com.hugg.domain.base.RequestVo
import com.hugg.domain.model.enums.DailyConditionType
import com.google.gson.annotations.SerializedName

data class CreateDailyRecordRequestVo(
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType = DailyConditionType.SOSO,
    @SerializedName("content")
    val content: String = ""
): RequestVo
