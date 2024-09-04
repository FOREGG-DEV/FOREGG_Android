package com.hugg.presentation.ui.main.information.subsidyDetail

import com.hugg.presentation.Event

sealed class SubsidyDetailEvent : Event{
    object OnClickBack : SubsidyDetailEvent()
}