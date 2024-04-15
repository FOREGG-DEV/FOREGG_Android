package com.foregg.presentation.ui.splash

import com.foregg.presentation.Event

sealed class SplashEvent : Event{
    object GoToMainEvent : SplashEvent()
    object GoToSignEvent : SplashEvent()
}