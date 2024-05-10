package com.foregg.data.mapper.account

import com.foregg.data.base.Mapper
import com.foregg.data.dto.account.AccountResponseListItem
import com.foregg.domain.model.response.account.AccountDetailResponseVo

object AccountDetailResponseMapper: Mapper.ResponseMapper<AccountResponseListItem, AccountDetailResponseVo> {

    override fun mapDtoToModel(type: AccountResponseListItem?): AccountDetailResponseVo {
        return type?.run {
            AccountDetailResponseVo(
                id = id,
                type = ledgerType,
                date = date,
                content = content,
                money = amount.toString(),
                round = count,
                memo = memo
            )
        }?: AccountDetailResponseVo()
    }
}