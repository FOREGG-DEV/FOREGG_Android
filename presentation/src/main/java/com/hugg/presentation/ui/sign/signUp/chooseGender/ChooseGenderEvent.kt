package com.hugg.presentation.ui.sign.signUp.chooseGender

import com.hugg.presentation.Event

sealed class ChooseGenderEvent : Event {
    object GoToBackEvent : ChooseGenderEvent()
    data class OnClickFemaleEvent(val ssn : String, val shareCode : String) : ChooseGenderEvent()
    data class OnClickMaleEvent(val ssn : String) : ChooseGenderEvent()
    object ErrorEmpty : ChooseGenderEvent()
}