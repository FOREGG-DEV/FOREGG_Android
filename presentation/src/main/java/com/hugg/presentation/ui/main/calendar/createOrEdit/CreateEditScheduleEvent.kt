package com.hugg.presentation.ui.main.calendar.createOrEdit

import com.hugg.domain.model.enums.CalendarDatePickerType
import com.hugg.presentation.Event

sealed class CreateEditScheduleEvent : Event{
    object GoToBackEvent : CreateEditScheduleEvent()
    object ShowSelectScheduleDialog : CreateEditScheduleEvent()
    data class ShowDatePickerDialogEvent(val type : CalendarDatePickerType) : CreateEditScheduleEvent()
    object ErrorExist : CreateEditScheduleEvent()
    object ErrorRepeatDate : CreateEditScheduleEvent()
    object ErrorBlankExist : CreateEditScheduleEvent()
}