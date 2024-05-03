package com.foregg.data.mapper.schedule

import com.foregg.data.base.Mapper
import com.foregg.data.dto.schedule.ScheduleListResponse
import com.foregg.domain.model.vo.ScheduleDetailVo

object ScheduleListResponseMapper: Mapper.ResponseMapper<ScheduleListResponse, List<ScheduleDetailVo>> {
    override fun mapDtoToModel(type: ScheduleListResponse?): List<ScheduleDetailVo> {
        return type?.run {
            records
        }?: emptyList()
    }
}