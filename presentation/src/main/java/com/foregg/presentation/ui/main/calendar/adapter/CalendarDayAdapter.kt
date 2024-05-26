package com.foregg.presentation.ui.main.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.presentation.databinding.IncludeItemDayBinding
import com.foregg.presentation.ui.main.calendar.viewHolder.CalendarDayViewHolder

class CalendarDayAdapter(
    private val listener : CalendarDayDelegate
) : ListAdapter<CalendarDayVo, RecyclerView.ViewHolder>(CalendarDayDiffCallBack()) {

    interface CalendarDayDelegate {
        fun onClickDay(day : String)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarDayViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDayViewHolder(binding, listener)
    }
}

class CalendarDayDiffCallBack : DiffUtil.ItemCallback<CalendarDayVo>() {
    override fun areItemsTheSame(oldItem: CalendarDayVo, newItem: CalendarDayVo): Boolean {
        return oldItem.day == newItem.day && oldItem.scheduleList == newItem.scheduleList
    }
    override fun areContentsTheSame(oldItem: CalendarDayVo, newItem: CalendarDayVo): Boolean = oldItem == newItem
}