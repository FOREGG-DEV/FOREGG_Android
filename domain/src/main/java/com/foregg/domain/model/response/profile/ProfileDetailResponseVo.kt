package com.foregg.domain.model.response.profile

import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.enums.SurgeryType

data class ProfileDetailResponseVo(
    val nickName : String = "",
    val surgeryType: SurgeryType = SurgeryType.THINK_SURGERY,
    val round : Int = 0,
    val startDate : String = "",
    val spouse : String = "",
) : DomainResponse
