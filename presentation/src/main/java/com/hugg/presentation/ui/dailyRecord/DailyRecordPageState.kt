package com.hugg.presentation.ui.dailyRecord

import com.hugg.domain.model.enums.DailyRecordTabType
import com.hugg.domain.model.response.SideEffectListItemVo
import com.hugg.domain.model.vo.DailyRecordResponseItemVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class DailyRecordPageState (
    val dailyRecordList: StateFlow<List<DailyRecordResponseItemVo>>,
    val dailyRecordTabType: StateFlow<DailyRecordTabType>,
    val sideEffectList: StateFlow<List<SideEffectListItemVo>>,
): PageState