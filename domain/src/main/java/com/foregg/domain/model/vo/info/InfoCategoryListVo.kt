package com.foregg.domain.model.vo.info

import com.foregg.domain.model.enums.InformationType

data class InfoCategoryListVo(
    val type : InformationType,
    val list : List<InfoItemVo>
)
