package com.hugg.domain.model.vo

import com.hugg.domain.base.DomainResponse
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.EmotionType

data class DailyRecordResponseItemVo (
    val id: Long = -1,
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val content: String = "",
    val date: String = "",
    val emotionType: EmotionType = EmotionType.DEFAULT
): DomainResponse