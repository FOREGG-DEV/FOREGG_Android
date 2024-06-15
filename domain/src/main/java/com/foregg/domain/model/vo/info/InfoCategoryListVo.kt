package com.foregg.domain.model.vo.info

import com.foregg.domain.model.enums.InfoCategoryType

data class InfoCategoryListVo(
    val type : InfoCategoryType,
    val list : List<InfoItemVo>
)
