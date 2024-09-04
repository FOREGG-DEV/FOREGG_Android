package com.hugg.presentation.ui.main.profile.ask

import com.hugg.presentation.Event

sealed class AskEvent : Event {
    object GoToBackEvent : AskEvent()
    object OnClickCopyEmailEvent : AskEvent()
}