package com.foregg.data.dto.profile

import com.foregg.data.base.DataDto
import com.foregg.domain.model.enums.SurgeryType
import com.google.gson.annotations.SerializedName

data class ProfileDetailResponse(
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("surgeryType")
    val surgeryType : SurgeryType = SurgeryType.THINK_SURGERY,
    @SerializedName("count")
    val count : Int? = 0,
    @SerializedName("startDate")
    val startDate : String? = "",
    @SerializedName("spouse")
    val spouse : String? = "",
    @SerializedName("ssn")
    val ssn : String = "",
) : DataDto
