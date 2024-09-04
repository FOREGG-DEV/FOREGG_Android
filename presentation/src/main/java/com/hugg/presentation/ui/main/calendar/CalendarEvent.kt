package com.hugg.presentation.ui.main.calendar

import com.hugg.presentation.Event

sealed class CalendarEvent : Event {
    object CreateScheduleEvent : CalendarEvent()
    object ErrorDelete : CalendarEvent()
}