package com.foregg.presentation.ui.main.account.createOrEdit

import com.foregg.presentation.Event

sealed class AccountCreateEditEvent : Event{
    object GoToBackEvent : AccountCreateEditEvent()
    object ShowDatePickerDialog : AccountCreateEditEvent()
    object ErrorNotExist : AccountCreateEditEvent()
    object ErrorBlankContent : AccountCreateEditEvent()
}