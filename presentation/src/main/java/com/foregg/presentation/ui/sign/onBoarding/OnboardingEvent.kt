package com.foregg.presentation.ui.sign.onBoarding

import com.foregg.presentation.Event

sealed class OnboardingEvent : Event{
    object GoToMainEvent : OnboardingEvent()
    data class GoToSignUpEvent(val token : String, val shareCode : String) : OnboardingEvent()
    object MoveNextEvent : OnboardingEvent()
    object KaKaoLoginEvent : OnboardingEvent()
}