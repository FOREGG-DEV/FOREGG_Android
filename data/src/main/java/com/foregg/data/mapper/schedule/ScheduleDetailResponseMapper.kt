package com.foregg.data.mapper.schedule

import com.foregg.data.base.Mapper
import com.foregg.domain.model.vo.ScheduleDetailVo

object ScheduleDetailResponseMapper: Mapper.ResponseMapper<ScheduleDetailVo, ScheduleDetailVo> {
    override fun mapDtoToModel(type: ScheduleDetailVo?): ScheduleDetailVo {
        return type ?: ScheduleDetailVo()
    }
}