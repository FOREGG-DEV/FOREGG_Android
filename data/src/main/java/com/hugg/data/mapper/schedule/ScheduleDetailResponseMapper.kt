package com.hugg.data.mapper.schedule

import com.hugg.data.base.Mapper
import com.hugg.domain.model.vo.ScheduleDetailVo

object ScheduleDetailResponseMapper: Mapper.ResponseMapper<ScheduleDetailVo, ScheduleDetailVo> {
    override fun mapDtoToModel(type: ScheduleDetailVo?): ScheduleDetailVo {
        return type ?: ScheduleDetailVo()
    }
}