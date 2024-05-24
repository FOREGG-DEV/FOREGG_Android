package com.foregg.domain.model.request.sign

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.SurgeryType
import com.google.gson.annotations.SerializedName

data class SignUpRequestVo(
    @SerializedName("surgeryType")
    val surgeryType : SurgeryType,
    @SerializedName("count")
    val count : Int?,
    @SerializedName("startAt")
    val startAt : String?,
    @SerializedName("spouseCode")
    val spouseCode : String,
    @SerializedName("ssn")
    val ssn : String,
) : RequestVo