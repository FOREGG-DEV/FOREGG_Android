package com.foregg.domain.model.vo

import com.foregg.domain.model.enums.GenderType

data class UserVo(
    val name : String = "",
    val ssn : String = "",
    val genderType: GenderType = GenderType.FEMALE,
    val spouse : String = "",
)
