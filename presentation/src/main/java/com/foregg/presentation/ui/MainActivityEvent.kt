package com.foregg.presentation.ui

import com.foregg.presentation.Event

sealed class MainActivityEvent : Event {
    object GoToCalendar : MainActivityEvent()
    object GoToAccount : MainActivityEvent()
    object GoToMain : MainActivityEvent()
    object GoToInfo : MainActivityEvent()
    object GoToProfile : MainActivityEvent()
    object GoToCreateDailyRecord : MainActivityEvent()
}