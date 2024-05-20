package com.foregg.presentation.ui.main.profile.ask

import com.foregg.presentation.Event

sealed class AskEvent : Event {
    object GoToBackEvent : AskEvent()
    object OnClickCopyEmailEvent : AskEvent()
}