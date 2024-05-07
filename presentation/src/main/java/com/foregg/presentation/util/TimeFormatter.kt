package com.foregg.presentation.util

import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Locale

object TimeFormatter {

    private const val YEAR_INDEX = 0
    private const val MONTH_INDEX = 1
    private const val DAY_INDEX = 2

    fun getToday(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun getYear(date : String) : String {
        val dates = date.split("-")
        return dates[YEAR_INDEX]
    }

    fun getMonth(date : String) : String {
        val dates = date.split("-")
        return dates[MONTH_INDEX]
    }

    fun getDay(date : String) : String {
        val dates = date.split("-")
        return dates[DAY_INDEX]
    }

    fun getKoreanDayOfWeek(dayOfWeek: DayOfWeek): String {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "월"
            DayOfWeek.TUESDAY -> "화"
            DayOfWeek.WEDNESDAY -> "수"
            DayOfWeek.THURSDAY -> "목"
            DayOfWeek.FRIDAY -> "금"
            DayOfWeek.SATURDAY -> "토"
            DayOfWeek.SUNDAY -> "일"
        }
    }

    fun formatStringTimeToObjectTime(time : String) : CreateScheduleTimeVo{
        val hour = time.split(":")[0].toInt()
        return if(hour >= 12) {
            CreateScheduleTimeVo(
                amOrPm = "오후",
                hour = (hour-12).toString(),
                minute = time.split(":")[1]
            )
        }
        else{
            CreateScheduleTimeVo(
                amOrPm = "오전",
                hour = hour.toString(),
                minute = time.split(":")[1]
            )
        }
    }

    fun formatObjectTimeToStringTime(time : CreateScheduleTimeVo) : String{
        val timeText = "${time.amOrPm} ${time.hour}:${time.minute}"
        return try {
            val inputFormat = SimpleDateFormat("a h:mm", Locale.getDefault())
            val date = inputFormat.parse(timeText)
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getDatesBetween(vo : ScheduleDetailVo): List<ScheduleDetailVo> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startLocalDate = LocalDate.parse(vo.startDate, formatter)
        val endLocalDate = LocalDate.parse(vo.endDate, formatter)

        val datesBetween = mutableListOf<ScheduleDetailVo>()
        var currentDate = startLocalDate

        while (!currentDate.isAfter(endLocalDate)) {
            if (isContainDayOfWeek(vo.repeatDate?.toList(), currentDate)) datesBetween.add(vo.copy(date = currentDate.toString()))
            currentDate = currentDate.plusDays(1)
        }

        return datesBetween
    }

    private fun isContainDayOfWeek(weekdays : List<String>?, day : LocalDate) : Boolean{
        val dayOfWeekKorean = getKoreanDayOfWeek(day.dayOfWeek)
        return weekdays?.contains("매일") == true ||
                weekdays?.contains(dayOfWeekKorean) == true
    }

    fun getDotsDate(dateString : String) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        val newFormat = DateTimeFormatter.ofPattern("yyyy. MM. dd")
        return date.format(newFormat)
    }

    fun getDashDate(dateString : String) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd")
        val date = LocalDate.parse(dateString, formatter)
        val newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(newFormat)
    }

    fun getPreviousMonthDate() : String {
        val today = LocalDate.now()
        val oneMonthAgo = today.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return oneMonthAgo.format(formatter)
    }
}