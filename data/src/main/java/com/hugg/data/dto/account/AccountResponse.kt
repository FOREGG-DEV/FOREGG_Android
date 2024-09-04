package com.hugg.data.dto.account

import com.hugg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("ledgerSummaryDTO")
    val ledgerSummaryDTO : AccountSummaryResponse = AccountSummaryResponse(),
    @SerializedName("ledgerResponseDTOS")
    val ledgerResponseDTOS : List<AccountResponseListItem> = emptyList()
) : DataDto
