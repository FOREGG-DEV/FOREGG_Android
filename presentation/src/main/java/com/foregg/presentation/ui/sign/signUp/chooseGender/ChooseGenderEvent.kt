package com.foregg.presentation.ui.sign.signUp.chooseGender

import com.foregg.presentation.Event

sealed class ChooseGenderEvent : Event {
    object GoToBackEvent : ChooseGenderEvent()
    object OnClickFemaleEvent : ChooseGenderEvent()
    object OnClickMaleEvent : ChooseGenderEvent()
}