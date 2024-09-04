package com.hugg.domain.model.vo.info

data class InfoItemVo(
    val url: String = "",
    val tags : List<String> = emptyList(),
    val image: String = ""
)
