package com.foregg.presentation.ui.dailyRecord

import com.foregg.presentation.Event

sealed class DailyRecordEvent: Event {
    object GoToCreateDailyRecordEvent: DailyRecordEvent()
    object GoToCreateSideEffectEvent: DailyRecordEvent()
    object OnClickBtnClose: DailyRecordEvent()
}