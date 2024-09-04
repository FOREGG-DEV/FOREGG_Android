package com.hugg.domain.model.request.sign

import com.hugg.domain.base.RequestVo

data class SaveForeggJwtRequestVo(
    val accessToken : String,
    val refreshToken : String
) : RequestVo
