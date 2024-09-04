package com.hugg.presentation.ui.main.account

import com.hugg.presentation.Event

sealed class AccountEvent : Event{
    object OnClickAddOrDeleteBtn : AccountEvent()
    data class ShowBottomSheetEvent(val startDay : String, val endDay : String) : AccountEvent()
    object ErrorNotExist : AccountEvent()
}