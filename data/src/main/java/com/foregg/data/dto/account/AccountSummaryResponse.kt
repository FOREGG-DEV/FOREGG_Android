package com.foregg.data.dto.account

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class AccountSummaryResponse(
    @SerializedName("totalExpense")
    val totalExpense : Int = 0,
    @SerializedName("subsidy")
    val subsidy : Int = 0,
    @SerializedName("personal")
    val personal : Int = 0
) : DataDto
