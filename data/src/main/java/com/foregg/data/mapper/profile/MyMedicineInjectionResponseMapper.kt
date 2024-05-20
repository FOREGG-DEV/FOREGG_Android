package com.foregg.data.mapper.profile

import com.foregg.data.base.Mapper
import com.foregg.data.dto.profile.MyMedicineInjectionItemResponse
import com.foregg.data.dto.profile.MyMedicineInjectionResponse
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo

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