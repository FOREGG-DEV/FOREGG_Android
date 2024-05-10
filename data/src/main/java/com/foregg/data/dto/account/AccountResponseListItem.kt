package com.foregg.data.dto.account

import com.foregg.data.base.DataDto
import com.foregg.domain.model.enums.AccountType
import com.google.gson.annotations.SerializedName

data class AccountResponseListItem(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("ledgerType")
    val ledgerType : AccountType = AccountType.PERSONAL_EXPENSE,
    @SerializedName("date")
    val date : String = "",
    @SerializedName("content")
    val content : String = "",
    @SerializedName("amount")
    val amount : Int = 0,
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("memo")
    val memo : String = "",
) : DataDto
