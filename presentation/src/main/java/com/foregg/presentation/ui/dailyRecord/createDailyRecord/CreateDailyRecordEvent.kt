package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import com.foregg.presentation.Event

sealed class CreateDailyRecordEvent: Event {
    object InsufficientEmotionDataEvent: CreateDailyRecordEvent()
    object InsufficientTextDataEvent: CreateDailyRecordEvent()
    object OnClickBtnClose: CreateDailyRecordEvent()
    object ExistDailyRecordEvent: CreateDailyRecordEvent()
    object SuccessEditDailyRecordEvent: CreateDailyRecordEvent()
}