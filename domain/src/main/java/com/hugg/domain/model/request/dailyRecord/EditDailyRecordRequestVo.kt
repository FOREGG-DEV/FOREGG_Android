package com.hugg.domain.model.request.dailyRecord

data class EditDailyRecordRequestVo(
    val id: Long,
    val request: CreateDailyRecordRequestVo
)