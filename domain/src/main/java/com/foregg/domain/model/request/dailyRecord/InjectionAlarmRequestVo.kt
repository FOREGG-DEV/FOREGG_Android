package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo

data class InjectionAlarmRequestVo(
    val id : Long,
    val time : String
) : RequestVo
