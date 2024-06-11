package com.foregg.presentation.ui.main.information

import com.foregg.presentation.Event

sealed class InformationEvent: Event {
    object GoToPregnancyDetailEvent: InformationEvent()
    object GoToInfertilityDetailEvent: InformationEvent()
    object GoBackEvent: InformationEvent()
}