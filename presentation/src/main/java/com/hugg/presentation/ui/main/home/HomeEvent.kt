package com.hugg.presentation.ui.main.home

import com.hugg.presentation.Event

sealed class HomeEvent: Event {
    object GoToDailyRecordEvent : HomeEvent()
    object GoToChallengeEvent: HomeEvent()
    object GoToCalendarEvent: HomeEvent()
    object GoToCreateEditScheduleEvent: HomeEvent()
    data class ShowWeekEndDialog(val isSuccess : Boolean) : HomeEvent()
}