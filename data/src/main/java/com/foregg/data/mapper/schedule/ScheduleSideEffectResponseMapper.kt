package com.foregg.data.mapper.schedule

import com.foregg.data.base.Mapper
import com.foregg.data.dto.schedule.ScheduleSideEffectResponse
import com.foregg.domain.model.vo.MedicalRecord
import com.foregg.domain.model.vo.SideEffectVo

object ScheduleSideEffectResponseMapper: Mapper.ResponseMapper<ScheduleSideEffectResponse, MedicalRecord> {
    override fun mapDtoToModel(type: ScheduleSideEffectResponse?): MedicalRecord {
        return type?.run {
            MedicalRecord(
                medicalRecord = medicalRecord ?: "",
                medicalSideEffect = sideEffects?.map { SideEffectVo(content = it.content) } ?: emptyList()
            )
        }?: MedicalRecord()
    }
}