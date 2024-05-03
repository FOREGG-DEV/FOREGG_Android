package com.foregg.domain.model.vo

import com.foregg.domain.model.enums.AccountType

data class AccountCardVo(
    val date : String,
    val round : String,
    val type : AccountType,
    val title : String,
    val money : String,
)
