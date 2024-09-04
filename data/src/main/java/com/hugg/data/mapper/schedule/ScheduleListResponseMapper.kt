package com.hugg.data.mapper.schedule

import com.hugg.data.base.Mapper
import com.hugg.data.dto.schedule.ScheduleListResponse
import com.hugg.domain.model.vo.ScheduleDetailVo

object ScheduleListResponseMapper: Mapper.ResponseMapper<ScheduleListResponse, List<ScheduleDetailVo>> {
    override fun mapDtoToModel(type: ScheduleListResponse?): List<ScheduleDetailVo> {
        return type?.run {
            records
        }?: emptyList()
    }
}