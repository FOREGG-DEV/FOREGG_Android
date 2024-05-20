package com.foregg.domain.model.response.account

import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.vo.account.AccountCardVo

data class AccountResponseVo(
    val allExpendMoney : Int = 0,
    val subsidyMoney : Int = 0,
    val personalMoney : Int = 0,
    val accountList : List<AccountCardVo> = emptyList()
) : DomainResponse
