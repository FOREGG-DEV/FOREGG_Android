package com.foregg.domain.model.response.profile

import com.foregg.domain.base.DomainResponse

data class MyMedicineInjectionResponseVo(
    val id : Long = -1,
    val date : String = "",
    val startDate : String = "",
    val endDate : String = "",
    val repeatDays : String = "",
    val name : String = "",
) : DomainResponse
