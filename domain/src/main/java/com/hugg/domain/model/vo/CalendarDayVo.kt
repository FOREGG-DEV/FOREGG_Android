package com.hugg.domain.model.vo

import com.hugg.domain.model.enums.DayType

data class CalendarDayVo(
    val dayType: DayType = DayType.NORMAL,
    val day : String = "",
    val scheduleList : List<ScheduleDetailVo> = emptyList(),
    val isToday : Boolean = false,
    val isClicked : Boolean = false
)