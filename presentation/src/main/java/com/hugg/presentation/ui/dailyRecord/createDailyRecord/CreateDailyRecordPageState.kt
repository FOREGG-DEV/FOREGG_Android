package com.hugg.presentation.ui.dailyRecord.createDailyRecord

import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateDailyRecordPageState (
    val dailyRecordText: StateFlow<String>,
    val questionText: StateFlow<String>,
    val isSelectedEmotion: StateFlow<DailyConditionType>,
    var contentText: MutableStateFlow<String>,
    val isEditMode : StateFlow<Boolean>,
): PageState