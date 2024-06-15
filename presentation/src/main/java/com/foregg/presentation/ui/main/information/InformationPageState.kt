package com.foregg.presentation.ui.main.information

import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class InformationPageState (
    val infoList: StateFlow<List<InfoCategoryListVo>>
): PageState