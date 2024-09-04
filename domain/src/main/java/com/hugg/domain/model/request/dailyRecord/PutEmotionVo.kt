package com.hugg.domain.model.request.dailyRecord

import com.hugg.domain.base.RequestVo

data class PutEmotionVo(
    val id: Long,
    val request: EmotionVo
): RequestVo
