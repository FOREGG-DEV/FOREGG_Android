package com.foregg.presentation.ui.dailyRecord

import com.foregg.data.dto.dailyRecord.DailyRecordResponseItem
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class DailyRecordPageState (
    val dailyRecordList: StateFlow<List<DailyRecordResponseItemVo>>
): PageState