package com.hugg.domain.model.request.account

import com.hugg.domain.base.RequestVo
import com.hugg.domain.model.enums.AccountType
import com.google.gson.annotations.SerializedName

data class AccountCreateRequestVo(
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
