package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo

data class SignUpWithTokenRequestVo(
    val accessToken : String,
    val signUpRequestVo: SignUpRequestVo
) : RequestVo
