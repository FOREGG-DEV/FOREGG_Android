package com.foregg.presentation.ui.main.calendar

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.enums.DayType
import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.usecase.schedule.DeleteScheduleUseCase
import com.foregg.domain.usecase.schedule.GetScheduleListUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.TimeFormatter
import com.foregg.presentation.util.toList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val getScheduleListUseCase: GetScheduleListUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<CalendarPageState>() {

    companion object{
        const val JANUARY = 1
        const val DECEMBER = 12
    }

    private val selectedYearAndMonthStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val calendarDayListStateFlow : MutableStateFlow<List<CalendarDayVo>> = MutableStateFlow(
        emptyList()
    )
    private val selectedDayStateFlow : MutableStateFlow<String> = MutableStateFlow(TimeFormatter.getDotsDate(TimeFormatter.getToday()))
    private val scheduleListStateFlow : MutableStateFlow<List<ScheduleDetailVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: CalendarPageState = CalendarPageState(
        selectedYearAndMonthStateFlow.asStateFlow(),
        calendarDayListStateFlow.asStateFlow(),
        selectedDayStateFlow.asStateFlow(),
        scheduleListStateFlow.asStateFlow()
    )

    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()

    fun setCalendar(){
        val today = TimeFormatter.getToday()
        year = TimeFormatter.getYear(today).toInt()
        month = TimeFormatter.getMonth(today).toInt()
        getScheduleList()
    }

    private fun getScheduleList(){
        viewModelScope.launch {
            getScheduleListUseCase(getRequest()).collect{
                resultResponse(it, ::updateCalendar, { ForeggLog.D("에러") }, true)
            }
        }
    }

    private fun getRequest() : String{
        val requestYear = String.format("%02d", year)
        val requestMonth = String.format("%02d", month)
        return "$requestYear-$requestMonth"
    }

    private fun updateCalendar(list : List<ScheduleDetailVo>){
        viewModelScope.launch {
            selectedYearAndMonthStateFlow.update { resourceProvider.getString(R.string.calendar_year_and_month, year, month) }
            calendarDayListStateFlow.update { getDayList(list) }
        }
    }

    private fun getDayList(list : List<ScheduleDetailVo>) : List<CalendarDayVo> {
        val dayList = getMonthDays(year, month, list)
        initScheduleList(TimeFormatter.getDashDate(selectedDayStateFlow.value), dayList)
        return getHeadDayList() + dayList
    }

    private fun getHeadDayList() : List<CalendarDayVo>{
        val list = resourceProvider.getStringArray(R.array.calendar_week_head).map {
            CalendarDayVo(
                dayType = DayType.HEAD,
                day = it
            )
        }

        return list
    }

    fun onClickNextMonth(){
        if(month == DECEMBER){
            year++
            month = JANUARY
        }
        else{
            month++
        }
        updateSelectedDay(YearMonth.of(year, month).atDay(1).toString())
        getScheduleList()
    }

    fun onClickPrevMonth(){
        if(month == JANUARY){
            year--
            month = DECEMBER
        }
        else{
            month--
        }
        updateSelectedDay(YearMonth.of(year, month).atDay(1).toString())
        getScheduleList()
    }

    fun onClickDay(day : String){
        changeClickedDay(day)
        updateSelectedDay(day)
        val schedule = calendarDayListStateFlow.value.find { it.day == day }
        updateScheduleList(splitRepeatTimesForList(schedule?.scheduleList ?: emptyList()))
    }

    private fun changeClickedDay(day : String){
        val newList = calendarDayListStateFlow.value.map {
            if(it.day == day) it.copy(isClicked = true) else it.copy(isClicked = false)
        }
        viewModelScope.launch {
            calendarDayListStateFlow.update { newList }
        }
    }

    private fun updateSelectedDay(day : String){
        viewModelScope.launch {
            selectedDayStateFlow.update { TimeFormatter.getDotsDate(day) }
        }
    }

    private fun initScheduleList(day : String, list : List<CalendarDayVo>){
        val schedule = list.find { it.day == day }
        updateScheduleList(splitRepeatTimesForList(schedule?.scheduleList ?: emptyList()))
    }

    private fun updateScheduleList(list : List<ScheduleDetailVo>){
        viewModelScope.launch {
            scheduleListStateFlow.update { list }
        }
    }

    private fun splitRepeatTimesForList(list : List<ScheduleDetailVo>): List<ScheduleDetailVo> {
        val result = mutableListOf<ScheduleDetailVo>()
        for (schedule in list) {
            val splitSchedules = splitRepeatTimes(schedule)
            result.addAll(splitSchedules)
        }
        return result.sortedBy { it.repeatTimes.first().time }
    }

    private fun splitRepeatTimes(schedule: ScheduleDetailVo): List<ScheduleDetailVo> {
        return schedule.repeatTimes.map { repeatTime ->
            schedule.copy(repeatTimes = listOf(repeatTime))
        }
    }

    fun onClickCreateSchedule(){
        emitEventFlow(CalendarEvent.CreateScheduleEvent)
    }

    fun onClickDelete(id : Long){
        viewModelScope.launch {
            deleteScheduleUseCase(id).collect{
                resultResponse(it, { handleDeleteSuccess(id) }, ::handleDeleteError, true)
            }
        }
    }

    private fun handleDeleteSuccess(id : Long){
        getScheduleList()
        updateScheduleList(scheduleListStateFlow.value.filter { it.id != id })
    }

    private fun handleDeleteError(error : String){
        when(error){
            StatusCode.RECORD.NO_EXIST_SCHEDULE -> emitEventFlow(CalendarEvent.ErrorDelete)
        }
    }

    private fun getMonthDays(year: Int, month: Int, list : List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val newScheduleList = getArrangeRepeatScheduleList(list)
        val dayList = mutableListOf<CalendarDayVo>()
        val startOfMonth = YearMonth.of(year, month).atDay(1)
        val endOfMonth = YearMonth.of(year, month).atEndOfMonth()

        var currentDate = startOfMonth
        while (currentDate.isBefore(endOfMonth) || currentDate.isEqual(endOfMonth)) {
            val dateString = currentDate.toString()
            val scheduleList = newScheduleList.filter { it.date == dateString }
            val isToday = dateString == TimeFormatter.getToday()
            val isClickedDay = dateString == TimeFormatter.getDashDate(selectedDayStateFlow.value)
            val calendarDayVo = CalendarDayVo(day = dateString, isToday = isToday, scheduleList = scheduleList, isClicked = isClickedDay)
            dayList.add(calendarDayVo)
            currentDate = currentDate.plusDays(1)
        }
        return getPreviousMonthDays(year, month, newScheduleList) + dayList + getNextMonthDays(year, month, newScheduleList)
    }

    private fun getArrangeRepeatScheduleList(list : List<ScheduleDetailVo>) : List<ScheduleDetailVo>{
        val normalScheduleList = list.filter { it.date != null }
        val repeatScheduleList = list.filter { it.date == null }
        val newList = mutableListOf<ScheduleDetailVo>()
        repeatScheduleList.forEach { vo ->
            newList.addAll(TimeFormatter.getDatesBetween(vo))
        }

        return normalScheduleList + newList
    }

    private fun getPreviousMonthDays(year: Int, month : Int, list : List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val startDay = YearMonth.of(year, month).atDay(1).dayOfWeek.value
        val lastDayOfPreviousMonth = YearMonth.of(year, month).minusMonths(1).atEndOfMonth()
        return (1 .. startDay).map { i ->
            val day = lastDayOfPreviousMonth.minusDays((startDay - i).toLong())
            val scheduleListForDay = list.filter { it.date == day.toString() }
            val isClickedDay = day.toString() == TimeFormatter.getDashDate(selectedDayStateFlow.value)
            CalendarDayVo(day = day.toString(), dayType = DayType.PREV_NEXT, scheduleList = scheduleListForDay, isClicked = isClickedDay)
        }
    }

    private fun getNextMonthDays(year: Int, month : Int, list : List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val endDay = if(YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value == 7) 0 else YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value
        val firstDayOfNextMonth = YearMonth.of(year, month).plusMonths(1).atDay(1)
        return (0 until (6 - endDay)).map {i ->
            val day = firstDayOfNextMonth.plusDays(i.toLong())
            val scheduleListForDay = list.filter { it.date == day.toString() }
            val isClickedDay = day.toString() == TimeFormatter.getDashDate(selectedDayStateFlow.value)
            CalendarDayVo(day = day.toString(), dayType = DayType.PREV_NEXT, scheduleList = scheduleListForDay, isClicked = isClickedDay)
        }
    }
}