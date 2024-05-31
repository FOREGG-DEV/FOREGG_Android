package com.foregg.presentation.ui.main.information

import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class InformationPageState (
    val infoList: StateFlow<Map<String, List<InfoItemVo>>>
): PageState