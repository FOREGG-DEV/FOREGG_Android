package com.hugg.domain.model.request.account

import com.hugg.domain.base.RequestVo

data class AccountEditRequestVo(
    val id : Long,
    val request : AccountCreateRequestVo
) : RequestVo
