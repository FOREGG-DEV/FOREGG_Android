package com.hugg.presentation.ui.dailyRecord

import com.hugg.presentation.Event

sealed class DailyRecordEvent: Event {
    object GoToCreateDailyRecordEvent: DailyRecordEvent()
    object GoToCreateSideEffectEvent: DailyRecordEvent()
    object OnClickBtnClose: DailyRecordEvent()
}