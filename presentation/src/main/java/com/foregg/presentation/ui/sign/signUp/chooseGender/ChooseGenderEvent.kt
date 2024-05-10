package com.foregg.presentation.ui.sign.signUp.chooseGender

import com.foregg.presentation.Event

sealed class ChooseGenderEvent : Event {
    object GoToBackEvent : ChooseGenderEvent()
    data class OnClickFemaleEvent(val ssn : String, val shareCode : String) : ChooseGenderEvent()
    data class OnClickMaleEvent(val ssn : String) : ChooseGenderEvent()
}