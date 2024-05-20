package com.foregg.domain.model.request.account

import com.foregg.domain.base.RequestVo

data class AccountEditRequestVo(
    val id : Long,
    val request : AccountCreateRequestVo
) : RequestVo
