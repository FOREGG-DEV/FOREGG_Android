package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo

data class SaveForeggJwtRequestVo(
    val accessToken : String,
    val refreshToken : String
) : RequestVo
