package com.foregg.domain.model.response

import com.foregg.domain.model.enums.RecordType

data class HomeRecordResponseVo(
    val id: Long,
    val recordType: RecordType,
    val name: String,
    val memo: String
)
