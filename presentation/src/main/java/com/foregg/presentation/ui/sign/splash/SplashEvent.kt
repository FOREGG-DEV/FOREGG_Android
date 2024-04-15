package com.foregg.presentation.ui.sign.splash

import com.foregg.presentation.Event

sealed class SplashEvent : Event{
    object GoToMainEvent : SplashEvent()
    object GoToSignEvent : SplashEvent()
}