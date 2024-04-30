package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse

data class SignResponseVo(
    val accessToken : String = ""
) : DomainResponse
