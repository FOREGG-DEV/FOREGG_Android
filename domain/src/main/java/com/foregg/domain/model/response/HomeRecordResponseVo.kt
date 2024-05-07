package com.foregg.domain.model.response

import com.foregg.domain.model.enums.RecordType
import com.google.gson.annotations.SerializedName

data class HomeRecordResponseVo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("recordType")
    val recordType: RecordType,
    @SerializedName("name")
    val name: String,
    @SerializedName("memo")
    val memo: String
)gi
