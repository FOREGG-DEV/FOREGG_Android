package com.foregg.data.mapper

import com.foregg.data.base.Mapper
import com.foregg.data.dto.SignResponse
import com.foregg.domain.model.response.SignResponseVo

object SignResponseMapper: Mapper.ResponseMapper<SignResponse, SignResponseVo> {

    override fun mapDtoToModel(type: SignResponse?): SignResponseVo {
        return type?.run {
            SignResponseVo(
                accessToken = accessToken ?: "",
                refreshToken = refreshToken ?: "",
                shareCode = spouseCode ?: ""
            )
        }?: SignResponseVo()
    }
}