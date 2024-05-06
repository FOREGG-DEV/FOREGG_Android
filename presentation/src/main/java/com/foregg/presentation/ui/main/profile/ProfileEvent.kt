package com.foregg.presentation.ui.main.profile

import com.foregg.presentation.Event

sealed class ProfileEvent : Event {
    object GoToEditProfileEvent : ProfileEvent()
    object GoToMyMedicineInjectionEvent : ProfileEvent()
    object GoToNoticeEvent : ProfileEvent()
    object GoToFAQEvent : ProfileEvent()
    object GoToAskEvent : ProfileEvent()
    object GoToPolicyEvent : ProfileEvent()
}