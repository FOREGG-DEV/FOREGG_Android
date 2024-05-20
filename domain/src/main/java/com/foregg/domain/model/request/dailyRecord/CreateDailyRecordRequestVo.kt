package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.DailyConditionType

data class CreateDailyRecordRequestVo(
    val dailyConditionType: DailyConditionType,
    val content: String
): RequestVo
