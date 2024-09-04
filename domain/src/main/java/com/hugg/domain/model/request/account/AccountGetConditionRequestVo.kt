package com.hugg.domain.model.request.account

import com.google.gson.annotations.SerializedName

data class AccountGetConditionRequestVo(
    @SerializedName("from")
    val from : String,
    @SerializedName("to")
    val to : String
)
