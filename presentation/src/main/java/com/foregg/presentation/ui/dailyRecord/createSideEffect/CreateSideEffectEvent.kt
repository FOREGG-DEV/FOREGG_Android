package com.foregg.presentation.ui.dailyRecord.createSideEffect

import com.foregg.presentation.Event

sealed class CreateSideEffectEvent: Event {
    object PopCreateSideFragment: CreateSideEffectEvent()
    object InSufficientTextEvent: CreateSideEffectEvent()
}