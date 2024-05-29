package com.foregg.presentation.ui.main.calendar.createOrEdit

import com.foregg.domain.model.enums.CalendarDatePickerType
import com.foregg.presentation.Event

sealed class CreateEditScheduleEvent : Event{
    object GoToBackEvent : CreateEditScheduleEvent()
    object ShowSelectScheduleDialog : CreateEditScheduleEvent()
    data class ShowDatePickerDialogEvent(val type : CalendarDatePickerType) : CreateEditScheduleEvent()
    object ErrorExist : CreateEditScheduleEvent()
    object ErrorRepeatDate : CreateEditScheduleEvent()
    object ErrorBlankExist : CreateEditScheduleEvent()
}