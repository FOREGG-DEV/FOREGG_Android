package com.foregg.domain.model.request.challenge

import com.foregg.domain.base.RequestVo

data class MarkChallengeVisitRequestVo(
    val id : Long,
    val time : String
) : RequestVo
