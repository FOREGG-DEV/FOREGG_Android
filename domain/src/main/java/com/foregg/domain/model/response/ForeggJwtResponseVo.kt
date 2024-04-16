package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse

data class ForeggJwtResponseVo (
    val accessToken : String = "",
    val refreshToken : String = "",
): DomainResponse {
    val isTokenValid = accessToken.isNotBlank() && refreshToken.isNotBlank()
}