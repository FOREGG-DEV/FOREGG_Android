package com.hugg.presentation.ui.sign.splash

import com.hugg.presentation.Event

sealed class SplashEvent : Event{
    object GoToMainEvent : SplashEvent()
    object GoToSignEvent : SplashEvent()
}