package com.foregg.presentation.ui.sign.signUp.female

import com.foregg.presentation.Event

sealed class SignUpFemaleEvent : Event {
    object GoToBackEvent : SignUpFemaleEvent()
    object ShowDatePickerDialogEvent : SignUpFemaleEvent()
    object GoToMainEvent : SignUpFemaleEvent()
    data class OnClickCopyCodeEvent(val code : String) : SignUpFemaleEvent()
    object ErrorEmptyDate : SignUpFemaleEvent()
}