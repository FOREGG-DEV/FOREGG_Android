package com.hugg.domain.model.request.challenge

import com.hugg.domain.base.RequestVo

data class MarkChallengeVisitRequestVo(
    val id : Long,
    val time : String
) : RequestVo
