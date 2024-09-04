package com.hugg.presentation.ui.sign.signUp.male

import com.hugg.presentation.Event

sealed class SignUpMaleEvent : Event {
    object GoToBackEvent : SignUpMaleEvent()
    object GoToMainEvent : SignUpMaleEvent()
    object ErrorShareCode : SignUpMaleEvent()
}