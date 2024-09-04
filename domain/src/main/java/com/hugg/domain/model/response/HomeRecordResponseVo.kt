package com.hugg.domain.model.response

import com.hugg.domain.base.DomainResponse
import com.hugg.domain.model.enums.RecordType
import com.google.gson.annotations.SerializedName

data class HomeRecordResponseVo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("recordType")
    val recordType: RecordType,
    @SerializedName("times")
    val times: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("memo")
    val memo: String
): DomainResponse
