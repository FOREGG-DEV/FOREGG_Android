package com.foregg.presentation.ui.main.calendar.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.DayType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemDayBinding
import com.foregg.presentation.ui.main.calendar.adapter.CalendarDayAdapter
import com.foregg.presentation.util.TimeFormatter
import com.foregg.presentation.util.px

class CalendarDayViewHolder(
    private val binding: IncludeItemDayBinding,
    private val listener: CalendarDayAdapter.CalendarDayDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var calendarDayVo : CalendarDayVo = CalendarDayVo()

    init {
        binding.apply {
            constraintLayoutDay.setOnClickListener {
                if(calendarDayVo.dayType != DayType.HEAD) listener.onClickDay(calendarDayVo.day)
            }
        }
    }

    fun bind(item : CalendarDayVo) {
        calendarDayVo = item
        binding.apply {
            if(item.day.isEmpty()) return
            if(item.dayType == DayType.HEAD){
                setHeadDay(item)
                return
            }
            if(item.dayType == DayType.PREV_NEXT){
                setPrevAndNextDay(item)
                return
            }
            setNormalDay(item)
        }
    }

    private fun setHeadDay(item : CalendarDayVo){
        binding.apply {
            textDay.setTextColor(root.context.getColor(R.color.gs_90))
            textDay.setTextAppearance(R.style.h3)
            textDay.text = item.day
            viewMedicineLine.visibility = View.GONE
            viewInjectionLine.visibility = View.GONE
            viewHospitalLine.visibility = View.GONE
            viewEtcLine.visibility = View.GONE
            constraintLayoutDay.setPadding(0,0,0,11.px)
        }
    }

    private fun setNormalDay(item: CalendarDayVo){
        binding.apply {
            viewClicked.visibility = if(item.isClicked) View.VISIBLE else View.INVISIBLE
            viewToday.visibility = if(item.isToday) View.VISIBLE else View.INVISIBLE
            textDay.setTextColor(root.context.getColor(getTextColor(item.isToday)))
            textDay.text = TimeFormatter.getDay(item.day).toInt().toString()
            if(item.scheduleList.isEmpty()) emptySchedule() else item.scheduleList.forEach { showScheduleLine(it) }
        }
    }

    private fun setPrevAndNextDay(item: CalendarDayVo){
        binding.apply {
            viewClicked.visibility = if(item.isClicked) View.VISIBLE else View.INVISIBLE
            viewToday.visibility = if(item.isToday) View.VISIBLE else View.INVISIBLE
            textDay.setTextColor(root.context.getColor(R.color.gs_30))
            textDay.text = TimeFormatter.getDay(item.day).toInt().toString()
            if(item.scheduleList.isEmpty()) emptySchedule() else item.scheduleList.forEach { showScheduleLine(it) }
        }
    }

    private fun getTextColor(isToday : Boolean) : Int{
        return if(isToday) R.color.white
               else R.color.gs_80
    }

    private fun showScheduleLine(schedule : ScheduleDetailVo){
        binding.apply {
            when(schedule.recordType){
                RecordType.MEDICINE -> viewMedicineLine.visibility = View.VISIBLE
                RecordType.INJECTION -> viewInjectionLine.visibility = View.VISIBLE
                RecordType.HOSPITAL -> viewHospitalLine.visibility = View.VISIBLE
                RecordType.ETC -> viewEtcLine.visibility = View.VISIBLE
            }
        }
    }

    private fun emptySchedule(){
        binding.apply {
            viewMedicineLine.visibility = View.INVISIBLE
            viewInjectionLine.visibility = View.INVISIBLE
            viewHospitalLine.visibility = View.INVISIBLE
            viewEtcLine.visibility = View.INVISIBLE
        }
    }
}