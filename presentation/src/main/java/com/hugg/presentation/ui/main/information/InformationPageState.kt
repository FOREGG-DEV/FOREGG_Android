package com.hugg.presentation.ui.main.information

import com.hugg.domain.model.vo.info.InfoCategoryListVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class InformationPageState (
    val infoList: StateFlow<List<InfoCategoryListVo>>
): PageState