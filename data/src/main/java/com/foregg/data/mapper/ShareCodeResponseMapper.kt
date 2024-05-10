package com.foregg.data.mapper

import com.foregg.data.base.Mapper
import com.foregg.data.dto.SignResponse
import com.foregg.domain.model.response.ShareCodeResponseVo

object ShareCodeResponseMapper: Mapper.ResponseMapper<SignResponse, ShareCodeResponseVo> {

    override fun mapDtoToModel(type: SignResponse?): ShareCodeResponseVo {
        return type?.run {
            ShareCodeResponseVo(
                shareCode = spouseCode ?: ""
            )
        }?: ShareCodeResponseVo()
    }
}