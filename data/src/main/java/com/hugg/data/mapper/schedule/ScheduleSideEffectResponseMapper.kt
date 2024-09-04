package com.hugg.data.mapper.schedule

import com.hugg.data.base.Mapper
import com.hugg.data.dto.schedule.ScheduleSideEffectResponse
import com.hugg.domain.model.vo.MedicalRecord
import com.hugg.domain.model.vo.SideEffectVo

object ScheduleSideEffectResponseMapper: Mapper.ResponseMapper<ScheduleSideEffectResponse, MedicalRecord> {
    override fun mapDtoToModel(type: ScheduleSideEffectResponse?): MedicalRecord {
        return type?.run {
            MedicalRecord(
                medicalRecord = medicalRecord ?: "",
                medicalSideEffect = sideEffects?.map { SideEffectVo(
                    id = it.id,
                    dateAndTime = it.date,
                    content = it.content
                ) } ?: emptyList()
            )
        }?: MedicalRecord()
    }
}