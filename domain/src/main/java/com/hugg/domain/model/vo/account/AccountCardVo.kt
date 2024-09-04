package com.hugg.domain.model.vo.account

import com.hugg.domain.model.enums.AccountType

data class AccountCardVo(
    val id : Long,
    val date : String,
    val round : Int,
    val type : AccountType,
    val title : String,
    val money : Int,
    val isSelected : Boolean = false
)
