package com.foregg.presentation.ui.main.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.vo.CalendarDayVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.presentation.databinding.IncludeItemDayBinding
import com.foregg.presentation.databinding.IncludeItemScheduleDetailBinding
import com.foregg.presentation.ui.main.calendar.viewHolder.CalendarDayViewHolder
import com.foregg.presentation.ui.main.calendar.viewHolder.ScheduleViewHolder

class ScheduleAdapter(
    private val listener : ScheduleDelegate
) : ListAdapter<ScheduleDetailVo, RecyclerView.ViewHolder>(ScheduleDiffCallBack()) {

    interface ScheduleDelegate {
        fun onClickDetail(id : Long, type : RecordType)
        fun onClickDelete(id : Long)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduleViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemScheduleDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding, listener)
    }
}

class ScheduleDiffCallBack : DiffUtil.ItemCallback<ScheduleDetailVo>() {
    override fun areItemsTheSame(oldItem: ScheduleDetailVo, newItem: ScheduleDetailVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ScheduleDetailVo, newItem: ScheduleDetailVo): Boolean = oldItem == newItem
}