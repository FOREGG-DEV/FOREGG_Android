package com.hugg.domain.model.response.account

import com.hugg.domain.base.DomainResponse
import com.hugg.domain.model.vo.account.AccountCardVo

data class AccountResponseVo(
    val allExpendMoney : Int = 0,
    val subsidyMoney : Int = 0,
    val personalMoney : Int = 0,
    val accountList : List<AccountCardVo> = emptyList()
) : DomainResponse
