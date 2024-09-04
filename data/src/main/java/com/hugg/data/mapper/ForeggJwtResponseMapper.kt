package com.hugg.data.mapper

import com.hugg.data.base.Mapper
import com.hugg.data.dto.ForeggJwtResponse
import com.hugg.domain.model.response.ForeggJwtResponseVo

object ForeggJwtResponseMapper: Mapper.ResponseMapper<ForeggJwtResponse, ForeggJwtResponseVo> {

    override fun mapDtoToModel(type: ForeggJwtResponse?): ForeggJwtResponseVo {
        return type?.run {
            ForeggJwtResponseVo(accessToken, refreshToken)
        }?: ForeggJwtResponseVo("","")
    }
}