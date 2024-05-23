package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo
import com.google.gson.annotations.SerializedName

data class SignUpMaleRequestVo(
    @SerializedName("spouseCode")
    val spouseCode : String,
    @SerializedName("ssn")
    val ssn : String,
) : RequestVo