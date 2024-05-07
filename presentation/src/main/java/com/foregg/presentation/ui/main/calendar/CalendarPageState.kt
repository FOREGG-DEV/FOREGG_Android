package com.foregg.presentation.ui.main.calendar

import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class CalendarPageState(
    val selectedYearAndMonth : StateFlow<String>,
    val calendarDayList : StateFlow<List<CalendarDayVo>>,
    val selectedDay : StateFlow<String>,
    val scheduleList : StateFlow<List<ScheduleDetailVo>>
) : PageState