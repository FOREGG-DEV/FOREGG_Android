package com.hugg.domain.model.response.account

import com.hugg.domain.model.enums.AccountType


data class AccountDetailResponseVo(
    val id : Long = -1,
    val type : AccountType = AccountType.PERSONAL_EXPENSE,
    val date : String = "",
    val content : String = "",
    val money : String = "",
    val round : Int = 0,
    val memo : String = ""
)
