package com.foregg.presentation.ui.dailyRecord

import com.foregg.presentation.Event

sealed class DailyRecordUseCase {
    object CreateDailyRecordEvent: Event
    object GetDailyRecordEvent: Event
}