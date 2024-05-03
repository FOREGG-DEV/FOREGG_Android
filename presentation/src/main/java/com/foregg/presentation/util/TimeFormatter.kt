package com.foregg.presentation.util

import com.foregg.domain.model.enums.DayType
import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
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

    //사이 날짜들 구하기
    fun getDatesBetween(startDate: String, endDate: String): List<LocalDate> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startLocalDate = LocalDate.parse(startDate, formatter)
        val endLocalDate = LocalDate.parse(endDate, formatter)

        val datesBetween = mutableListOf<LocalDate>()
        var currentDate = startLocalDate

        while (!currentDate.isAfter(endLocalDate)) {
            datesBetween.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }

        return datesBetween
    }
}