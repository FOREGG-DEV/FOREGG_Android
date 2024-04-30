package com.foregg.domain.model.request

import com.foregg.domain.base.RequestVo
import com.foregg.domain.model.enums.SurgeryType

data class SignUpRequestVo(
    val surgeryType : SurgeryType,
    val count : Int?,
    val startAt : String?
) : RequestVo