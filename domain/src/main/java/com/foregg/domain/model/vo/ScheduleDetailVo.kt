package com.foregg.domain.model.vo

import com.foregg.domain.model.enums.RecordType
import com.google.gson.annotations.SerializedName

data class ScheduleDetailVo(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("recordType")
    val recordType: RecordType = RecordType.MEDICINE,
    @SerializedName("name")
    val name : String = "",
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("startDate")
    val startDate : String? = null,
    @SerializedName("endDate")
    val endDate : String? = null,
    @SerializedName("repeatDate")
    val repeatDate : String? = null,
    @SerializedName("repeatTimes")
    val repeatTimes : List<RepeatTimeVo> = emptyList(),
    @SerializedName("dose")
    val dose : String? = null,
    @SerializedName("memo")
    val memo : String = "",
)