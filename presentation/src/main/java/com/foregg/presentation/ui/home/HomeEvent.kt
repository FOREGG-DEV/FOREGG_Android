package com.foregg.presentation.ui.home

import com.foregg.presentation.Event

sealed class HomeEvent: Event {
    object GoToDailyRecordEvent : HomeEvent()
    object GoToChallengeEvent: HomeEvent()
}