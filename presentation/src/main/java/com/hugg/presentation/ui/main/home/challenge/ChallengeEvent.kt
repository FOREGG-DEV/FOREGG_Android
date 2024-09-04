package com.hugg.presentation.ui.main.home.challenge

import com.hugg.presentation.Event

sealed class ChallengeEvent: Event {
    object OnClickBtnComplete: ChallengeEvent()
    object OnClickBtnBack: ChallengeEvent()
    data class ShowWeekEndDialog(val isSuccess : Boolean) : ChallengeEvent()
}