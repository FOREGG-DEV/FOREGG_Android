package com.foregg.data.mapper

import com.foregg.data.base.Mapper
import com.foregg.data.dto.HomeResponse
import com.foregg.domain.model.response.HomeResponseVo

object HomeResponseMapper: Mapper.ResponseMapper<HomeResponse, HomeResponseVo> {
    override fun mapDtoToModel(type: HomeResponse?): HomeResponseVo {
        return type?.run {
            HomeResponseVo(
                userName = userName,
                todayDate = todayDate,
                homeRecordResponseVo = homeRecordResponseVo
            )
        } ?: HomeResponseVo()
    }
}