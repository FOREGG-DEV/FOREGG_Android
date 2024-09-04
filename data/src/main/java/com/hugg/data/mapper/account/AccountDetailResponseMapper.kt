package com.hugg.data.mapper.account

import com.hugg.data.base.Mapper
import com.hugg.data.dto.account.AccountResponseListItem
import com.hugg.domain.model.response.account.AccountDetailResponseVo

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