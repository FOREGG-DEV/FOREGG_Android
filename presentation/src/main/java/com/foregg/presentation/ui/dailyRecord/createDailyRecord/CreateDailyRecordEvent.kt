package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import com.foregg.presentation.Event

sealed class CreateDailyRecordEvent: Event {
    object GoToCreateSideEffectEvent: CreateDailyRecordEvent()
    object GetDailyRecordDataEvent: CreateDailyRecordEvent()
    object InsufficientEmotionDataEvent: CreateDailyRecordEvent()
}