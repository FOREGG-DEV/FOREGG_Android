package com.hugg.presentation.ui.sign.onBoarding

import com.hugg.presentation.Event

sealed class OnboardingEvent : Event{
    object GoToMainEvent : OnboardingEvent()
    data class GoToSignUpEvent(val token : String) : OnboardingEvent()
    object MoveNextEvent : OnboardingEvent()
    object SkipEvent : OnboardingEvent()
    object MovePrevEvent : OnboardingEvent()
    object KaKaoLoginEvent : OnboardingEvent()
}