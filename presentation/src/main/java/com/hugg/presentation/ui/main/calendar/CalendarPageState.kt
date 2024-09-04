package com.hugg.presentation.ui.main.calendar

import com.hugg.domain.model.vo.CalendarDayVo
import com.hugg.domain.model.vo.ScheduleDetailVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class CalendarPageState(
    val selectedYearAndMonth : StateFlow<String>,
    val calendarDayList : StateFlow<List<CalendarDayVo>>,
    val selectedDay : StateFlow<String>,
    val scheduleList : StateFlow<List<ScheduleDetailVo>>
) : PageState