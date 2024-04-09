package com.foregg.presentation.ui

import com.foregg.presentation.Event

sealed class MainActivityEvent : Event {
    object GoToMain : MainActivityEvent()
    object GoToKnotList : MainActivityEvent()
    object GoToCreateKnot : MainActivityEvent()
    object GoToProfile : MainActivityEvent()
    object GoToSetting : MainActivityEvent()
}