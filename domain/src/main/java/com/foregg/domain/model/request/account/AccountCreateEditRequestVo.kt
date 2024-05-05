package com.foregg.domain.model.request.account

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.AccountType
import com.google.gson.annotations.SerializedName

data class AccountCreateEditRequestVo(
    @SerializedName("ledgerType")
    val ledgerType : AccountType,
    @SerializedName("date")
    val date : String,
    @SerializedName("content")
    val content : String,
    @SerializedName("amount")
    val amount : Int,
    @SerializedName("count")
    val count : Int,
    @SerializedName("memo")
    val memo : String = "",
) : RequestVo
