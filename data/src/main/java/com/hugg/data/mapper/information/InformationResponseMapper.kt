package com.hugg.data.mapper.information

import com.hugg.data.base.Mapper
import com.hugg.data.dto.information.InformationResponse
import com.hugg.domain.model.response.information.InformationResponseVo

object InformationResponseMapper: Mapper.ResponseMapper<List<InformationResponse>, List<InformationResponseVo>> {
    override fun mapDtoToModel(type: List<InformationResponse>?): List<InformationResponseVo> {
        return type?.run {
            map {
                InformationResponseVo(
                    informationType = it.informationType,
                    tag = it.tag,
                    image = it.image,
                    url = it.url
                )
            }
        } ?: emptyList()
    }
}