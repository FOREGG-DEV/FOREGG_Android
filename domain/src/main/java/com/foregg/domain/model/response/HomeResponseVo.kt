package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse

data class HomeResponseVo (
    val userName: String = "",
    val todayDate: String = "",
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList()
): DomainResponse