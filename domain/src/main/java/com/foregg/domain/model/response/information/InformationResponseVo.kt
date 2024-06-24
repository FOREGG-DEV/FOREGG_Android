package com.foregg.domain.model.response.information

import com.foregg.domain.model.enums.InformationType

data class InformationResponseVo(
    val informationType : InformationType = InformationType.NOTHING,
    val tag : List<String> = emptyList(),
    val image : String = "",
    val url : String = "",
)
