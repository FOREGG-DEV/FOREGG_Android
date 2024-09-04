package com.hugg.presentation.ui.main.profile.edit

import com.hugg.presentation.Event

sealed class EditMyInfoEvent : Event {
    object GoToBackEvent : EditMyInfoEvent()
    object ShowDatePickerDialogEvent : EditMyInfoEvent()
    data class OnClickCopyCodeEvent(val code : String) : EditMyInfoEvent()
}