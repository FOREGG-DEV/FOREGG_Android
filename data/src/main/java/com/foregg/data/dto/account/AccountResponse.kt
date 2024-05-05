package com.foregg.data.dto.account

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("ledgerSummaryDTO")
    val ledgerSummaryDTO : AccountSummaryResponse = AccountSummaryResponse(),
    @SerializedName("ledgerResponseDTOS")
    val ledgerResponseDTOS : List<AccountResponseListItem> = emptyList()
) : DataDto
