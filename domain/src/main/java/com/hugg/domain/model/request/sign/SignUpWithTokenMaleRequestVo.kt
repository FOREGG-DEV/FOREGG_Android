package com.hugg.domain.model.request.sign

import com.hugg.domain.base.RequestVo

data class SignUpWithTokenMaleRequestVo(
    val accessToken : String,
    val signUpMaleRequestVo: SignUpMaleRequestVo
) : RequestVo