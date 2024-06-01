package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.enums.DailyConditionType

data class HomeResponseVo (
    val userName: String = "",
    val spouseName: String = "",
    val todayDate: String = "",
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList(),
    val ssn: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val dailyContent: String = "",
    val latestMedicalRecord: String = "",
    val medicalRecordId: Long = -1
): DomainResponse