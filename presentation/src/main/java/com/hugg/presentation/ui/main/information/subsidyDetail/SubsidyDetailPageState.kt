package com.hugg.presentation.ui.main.information.subsidyDetail

import com.hugg.domain.model.vo.info.InfoItemVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class SubsidyDetailPageState (
    val subsidyList: StateFlow<List<InfoItemVo>>
): PageState