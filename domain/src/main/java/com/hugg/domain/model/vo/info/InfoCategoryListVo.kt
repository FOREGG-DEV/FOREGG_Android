package com.hugg.domain.model.vo.info

import com.hugg.domain.model.enums.InformationType

data class InfoCategoryListVo(
    val type : InformationType,
    val list : List<InfoItemVo>
)
