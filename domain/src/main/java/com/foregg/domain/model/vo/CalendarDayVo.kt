package com.foregg.domain.model.vo

import com.foregg.domain.model.enums.DayType

data class CalendarDayVo(
    val dayType: DayType = DayType.NORMAL,
    val day : String = "",
    val scheduleList : List<ScheduleDetailVo> = emptyList(),
    val isToday : Boolean = false,
    val isClicked : Boolean = false
)