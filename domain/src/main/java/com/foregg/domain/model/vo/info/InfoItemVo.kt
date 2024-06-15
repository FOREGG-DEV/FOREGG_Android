package com.foregg.domain.model.vo.info

data class InfoItemVo(
    val url: String = "",
    val tags : List<String> = emptyList(),
    val image: String = ""
)
