package com.foregg.presentation.ui.main.profile.account

import com.foregg.presentation.Event

sealed class ProfileAccountEvent : Event {
    object GoToBackEvent : ProfileAccountEvent()
    object OnClickLogoutEvent : ProfileAccountEvent()
    object OnClickUnregisterEvent : ProfileAccountEvent()
    object CompleteLogoutEvent : ProfileAccountEvent()
    object CompleteUnregisterEvent : ProfileAccountEvent()
}