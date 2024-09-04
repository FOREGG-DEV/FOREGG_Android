package com.hugg.data.mapper

import com.hugg.data.base.Mapper
import com.hugg.data.dto.HomeResponse
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.response.HomeResponseVo

object HomeResponseMapper: Mapper.ResponseMapper<HomeResponse, HomeResponseVo> {
    override fun mapDtoToModel(type: HomeResponse?): HomeResponseVo {
        return type?.run {
            HomeResponseVo(
                userName = userName,
                spouseName = spouseName ?: "",
                todayDate = todayDate,
                homeRecordResponseVo = homeRecordResponseVo,
                ssn = ssn,
                dailyConditionType = dailyConditionType ?: DailyConditionType.DEFAULT,
                dailyContent = dailyContent ?: "",
                latestMedicalRecord = latestMedicalRecord ?: "",
                medicalRecordId = medicalRecordId
            )
        } ?: HomeResponseVo()
    }
}