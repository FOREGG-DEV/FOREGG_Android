package com.foregg.presentation.ui.sign.signUp.male

import com.foregg.presentation.Event

sealed class SignUpMaleEvent : Event {
    object GoToBackEvent : SignUpMaleEvent()
}