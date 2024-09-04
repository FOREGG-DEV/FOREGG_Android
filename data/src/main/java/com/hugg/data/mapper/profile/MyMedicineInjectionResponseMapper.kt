package com.hugg.data.mapper.profile

import com.hugg.data.base.Mapper
import com.hugg.data.dto.profile.MyMedicineInjectionResponse
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo

object MyMedicineInjectionResponseMapper: Mapper.ResponseMapper<MyMedicineInjectionResponse, List<MyMedicineInjectionResponseVo>> {

    override fun mapDtoToModel(type: MyMedicineInjectionResponse?): List<MyMedicineInjectionResponseVo> {
        return type?.run {
            myPageRecordResponseDTO.map {
                MyMedicineInjectionResponseVo(
                    id = it.id,
                    date = it.date ?: "",
                    startDate = it.startDate ?: "",
                    endDate = it.endDate ?: "",
                    repeatDays = it.repeatDays ?: "",
                    name = it.name
                )
            }
        } ?: emptyList()
    }
}