package com.hugg.domain.model.response

import com.hugg.domain.base.DomainResponse

data class SignResponseVo(
    val accessToken : String = "",
    val refreshToken : String = "",
    val shareCode : String = "",
) : DomainResponse
