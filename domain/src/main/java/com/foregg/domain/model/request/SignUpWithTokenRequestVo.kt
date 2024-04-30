package com.foregg.domain.model.request

import com.foregg.domain.base.RequestVo

data class SignUpWithTokenRequestVo(
    val accessToken : String,
    val signUpRequestVo: SignUpRequestVo
) : RequestVo
