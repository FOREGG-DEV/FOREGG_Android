package com.hugg.domain.model.request.dailyRecord

import com.hugg.domain.base.RequestVo

data class InjectionAlarmRequestVo(
    val id : Long,
    val time : String
) : RequestVo
