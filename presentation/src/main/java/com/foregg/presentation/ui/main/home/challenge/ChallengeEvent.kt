package com.foregg.presentation.ui.main.home.challenge

import com.foregg.presentation.Event

sealed class ChallengeEvent: Event {
    object OnClickBtnComplete: ChallengeEvent()
    object OnClickBtnBack: ChallengeEvent()
    data class ShowWeekEndDialog(val isSuccess : Boolean) : ChallengeEvent()
}