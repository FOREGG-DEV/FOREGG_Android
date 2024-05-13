package com.foregg.presentation.ui.main.profile.edit

import com.foregg.presentation.Event

sealed class EditMyInfoEvent : Event {
    object GoToBackEvent : EditMyInfoEvent()
    object ShowDatePickerDialogEvent : EditMyInfoEvent()
    data class OnClickCopyCodeEvent(val code : String) : EditMyInfoEvent()
}