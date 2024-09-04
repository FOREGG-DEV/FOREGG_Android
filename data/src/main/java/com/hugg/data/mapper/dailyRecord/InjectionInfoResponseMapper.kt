package com.hugg.data.mapper.dailyRecord

import com.hugg.data.base.Mapper
import com.hugg.data.dto.dailyRecord.InjectionInfoResponse
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo

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