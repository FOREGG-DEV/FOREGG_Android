package com.foregg.presentation.ui.main.information.subsidyDetail

import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class SubsidyDetailPageState (
    val subsidyList: StateFlow<List<InfoItemVo>>
): PageState