package com.foregg.presentation.ui.main.calendar

import com.foregg.presentation.Event

sealed class CalendarEvent : Event {
    object CreateScheduleEvent : CalendarEvent()
    object ErrorDelete : CalendarEvent()
}