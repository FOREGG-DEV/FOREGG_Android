package com.foregg.presentation.ui.main.account

import com.foregg.presentation.Event

sealed class AccountEvent : Event{
    object OnClickAddOrDeleteBtn : AccountEvent()
}