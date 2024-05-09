package com.foregg.domain.model.response

data class HomeResponseVo (
    val userName: String = "",
    val todayDate: String = "",
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList()
)