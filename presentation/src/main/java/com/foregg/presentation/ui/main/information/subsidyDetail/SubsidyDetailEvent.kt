package com.foregg.presentation.ui.main.information.subsidyDetail

import com.foregg.presentation.Event

sealed class SubsidyDetailEvent : Event{
    object OnClickBack : SubsidyDetailEvent()
}