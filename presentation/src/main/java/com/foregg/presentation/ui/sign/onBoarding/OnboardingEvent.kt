package com.foregg.presentation.ui.sign.onBoarding

import com.foregg.presentation.Event

sealed class OnboardingEvent : Event{
    object GoToMainEvent : OnboardingEvent()
    data class GoToSignUpEvent(val token : String) : OnboardingEvent()
    object MoveNextEvent : OnboardingEvent()
    object SkipEvent : OnboardingEvent()
    object MovePrevEvent : OnboardingEvent()
    object KaKaoLoginEvent : OnboardingEvent()
}