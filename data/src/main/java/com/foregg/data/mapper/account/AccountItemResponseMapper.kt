package com.foregg.data.mapper.account

import com.foregg.data.base.Mapper
import com.foregg.data.dto.account.AccountResponse
import com.foregg.domain.model.response.AccountResponseVo
import com.foregg.domain.model.vo.AccountCardVo

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