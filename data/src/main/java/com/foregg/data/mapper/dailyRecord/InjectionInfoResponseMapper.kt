package com.foregg.data.mapper.dailyRecord

import com.foregg.data.base.Mapper
import com.foregg.data.dto.dailyRecord.InjectionInfoResponse
import com.foregg.domain.model.response.daily.InjectionInfoResponseVo

object InjectionInfoResponseMapper: Mapper.ResponseMapper<InjectionInfoResponse, InjectionInfoResponseVo> {
    override fun mapDtoToModel(type: InjectionInfoResponse?): InjectionInfoResponseVo {
        return type?.run {
            InjectionInfoResponseVo(
                name = name,
                description = description ?: "",
                image = image ?: "",
                time = time
            )
        } ?: InjectionInfoResponseVo()
    }
}