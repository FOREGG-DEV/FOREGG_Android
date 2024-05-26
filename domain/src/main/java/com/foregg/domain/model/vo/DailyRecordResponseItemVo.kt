package com.foregg.domain.model.vo

import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.EmotionType

data class DailyRecordResponseItemVo (
    val id: Long = -1,
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val content: String = "",
    val date: String = "",
    val emotionType: EmotionType = EmotionType.DEFAULT
): DomainResponse