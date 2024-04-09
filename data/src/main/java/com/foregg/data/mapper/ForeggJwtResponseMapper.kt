package com.foregg.data.mapper

import com.foregg.data.base.Mapper
import com.foregg.data.dto.ForeggJwtResponse
import com.foregg.domain.model.response.ForeggJwtResponseVo

object ForeggJwtResponseMapper: Mapper.ResponseMapper<ForeggJwtResponse, ForeggJwtResponseVo> {

    override fun mapDtoToModel(type: ForeggJwtResponse?): ForeggJwtResponseVo {
        return type?.run {
            ForeggJwtResponseVo(accessToken, refreshToken)
        }?: ForeggJwtResponseVo("","")
    }
}