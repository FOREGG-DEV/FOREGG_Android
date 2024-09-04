package com.hugg.domain.model.request.sign

import com.hugg.domain.base.RequestVo

data class ForeggJwtReIssueRequestVo(
    val refreshToken : String
): RequestVo