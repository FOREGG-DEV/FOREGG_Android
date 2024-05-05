package com.foregg.domain.model.response.account

import com.foregg.domain.model.enums.AccountType


data class AccountDetailResponseVo(
    val id : Long = -1,
    val type : AccountType = AccountType.PERSONAL_EXPENSE,
    val date : String = "",
    val content : String = "",
    val money : String = "",
    val round : Int = 0,
    val memo : String = ""
)
