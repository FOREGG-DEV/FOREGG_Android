package com.hugg.domain.model.request.sign

import com.hugg.domain.base.RequestVo

data class SignUpWithTokenRequestVo(
    val accessToken : String,
    val signUpRequestVo: SignUpRequestVo
) : RequestVo
