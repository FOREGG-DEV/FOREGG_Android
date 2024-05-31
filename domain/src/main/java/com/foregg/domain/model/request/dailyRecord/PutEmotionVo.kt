package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.EmotionType

data class PutEmotionVo(
    val id: Long,
    val request: EmotionVo
): RequestVo
