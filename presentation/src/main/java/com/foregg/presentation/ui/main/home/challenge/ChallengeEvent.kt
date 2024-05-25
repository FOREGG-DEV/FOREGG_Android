package com.foregg.presentation.ui.main.home.challenge

import com.foregg.presentation.Event

sealed class ChallengeEvent: Event {
    object OnClickBtnBack: ChallengeEvent()
}