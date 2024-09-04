package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.AccountResponse
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.vo.account.AccountCardVo

object AccountItemResponseMapper: Mapper.ResponseMapper<AccountResponse, AccountResponseVo> {
    override fun mapDtoToModel(type: AccountResponse?): AccountResponseVo {
        return type?.run {
            AccountResponseVo(
                allExpendMoney = ledgerSummaryDTO.totalExpense,
                subsidyMoney = ledgerSummaryDTO.subsidy,
                personalMoney = ledgerSummaryDTO.personal,
                accountList = ledgerResponseDTOS.map {
                    AccountCardVo(
                        id = it.id,
                        date = it.date,
                        round = it.count,
                        type = it.ledgerType,
                        title = it.content,
                        money = it.amount,
                    )
                }
            )
        } ?: AccountResponseVo()
    }
}