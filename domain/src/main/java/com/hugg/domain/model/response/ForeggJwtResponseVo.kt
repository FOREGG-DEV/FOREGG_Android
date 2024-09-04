package com.hugg.domain.model.response

import com.hugg.domain.base.DomainResponse

data class ForeggJwtResponseVo (
    val accessToken : String = "",
    val refreshToken : String = "",
): DomainResponse {
    val isTokenValid = accessToken.isNotBlank() && refreshToken.isNotBlank()
}