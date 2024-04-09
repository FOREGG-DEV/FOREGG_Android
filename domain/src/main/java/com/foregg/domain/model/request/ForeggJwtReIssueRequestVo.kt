package com.foregg.domain.model.request

import com.foregg.domain.base.RequestVo

data class ForeggJwtReIssueRequestVo(
    val refreshToken : String
): RequestVo