package com.hugg.presentation.ui.main.injection

import com.hugg.presentation.Event

sealed class InjectionEvent : Event {
    object GoToHomeEvent : InjectionEvent()
    object SuccessShareToast : InjectionEvent()
    object ErrorShareToast : InjectionEvent()
}