package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateDailyRecordPageState (
    val dailyRecordText: StateFlow<String>,
    val questionText: StateFlow<String>,
    val isSelectedEmotion: StateFlow<DailyConditionType>,
    var contentText: MutableStateFlow<String>
): PageState