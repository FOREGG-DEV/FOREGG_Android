package com.foregg.presentation.ui.main.injection

import com.foregg.presentation.Event

sealed class InjectionEvent : Event {
    object GoToHomeEvent : InjectionEvent()
    object SuccessShareToast : InjectionEvent()
    object ErrorShareToast : InjectionEvent()
}