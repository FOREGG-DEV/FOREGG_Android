package com.foregg.presentation.ui.sign.onBoarding

import com.foregg.presentation.Event

sealed class OnboardingEvent : Event{
    object GoToMainEvent : OnboardingEvent()
    object GoToSignUpEvent : OnboardingEvent()
}