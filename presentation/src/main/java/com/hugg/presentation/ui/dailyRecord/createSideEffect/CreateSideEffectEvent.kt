package com.hugg.presentation.ui.dailyRecord.createSideEffect

import com.hugg.presentation.Event

sealed class CreateSideEffectEvent: Event {
    object PopCreateSideFragment: CreateSideEffectEvent()
    object InSufficientTextEvent: CreateSideEffectEvent()
}