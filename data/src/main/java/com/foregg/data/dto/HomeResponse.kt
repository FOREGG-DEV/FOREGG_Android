package com.foregg.data.dto

import com.foregg.data.base.DataDto
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("userName")
    val userName: String = "",
    @SerializedName("spouseName")
    val spouseName: String? = "",
    @SerializedName("todayDate")
    val todayDate: String = "",
    @SerializedName("homeRecordResponseDTO")
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList(),
    @SerializedName("ssn")
    val ssn: String = "",
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType? = DailyConditionType.DEFAULT,
    @SerializedName("dailyContent")
    val dailyContent: String? = "",
    @SerializedName("latestMedicalRecord")
    val latestMedicalRecord: String? = "",
    @SerializedName("medicalRecordId")
    val medicalRecordId: Long = -1
) : DataDto
