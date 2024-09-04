package com.hugg.presentation.ui.main.profile.account

import com.hugg.presentation.Event

sealed class ProfileAccountEvent : Event {
    object GoToBackEvent : ProfileAccountEvent()
    object OnClickLogoutEvent : ProfileAccountEvent()
    object OnClickUnregisterEvent : ProfileAccountEvent()
    object CompleteLogoutEvent : ProfileAccountEvent()
    object CompleteUnregisterEvent : ProfileAccountEvent()
}