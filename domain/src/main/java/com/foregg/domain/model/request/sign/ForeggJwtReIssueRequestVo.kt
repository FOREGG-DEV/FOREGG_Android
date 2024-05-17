package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo

data class ForeggJwtReIssueRequestVo(
    val refreshToken : String
): RequestVo