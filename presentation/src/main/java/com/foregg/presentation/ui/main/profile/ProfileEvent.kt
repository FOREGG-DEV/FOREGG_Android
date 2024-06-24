package com.foregg.presentation.ui.main.profile

import com.foregg.presentation.Event

sealed class ProfileEvent : Event {
    object GoToEditProfileEvent : ProfileEvent()
    object GoToMyMedicineInjectionEvent : ProfileEvent()
    data class GoToNoticeEvent(val url : String) : ProfileEvent()
    data class GoToFAQEvent(val url : String) : ProfileEvent()
    object GoToAskEvent : ProfileEvent()
    data class GoToPolicyEvent(val url : String) : ProfileEvent()
    object GoToAccountSettingEvent : ProfileEvent()
}