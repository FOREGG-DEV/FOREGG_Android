package com.foregg.domain.model.vo.account

import com.foregg.domain.model.enums.AccountType

data class AccountCardVo(
    val id : Long,
    val date : String,
    val round : Int,
    val type : AccountType,
    val title : String,
    val money : Int,
    val isSelected : Boolean = false
)
