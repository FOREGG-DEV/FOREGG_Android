package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo

data class SignUpWithTokenMaleRequestVo(
    val accessToken : String,
    val signUpMaleRequestVo: SignUpMaleRequestVo
) : RequestVo