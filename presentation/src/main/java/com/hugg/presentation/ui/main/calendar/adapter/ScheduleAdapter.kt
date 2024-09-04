package com.hugg.presentation.ui.main.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.vo.ScheduleDetailVo
import com.hugg.presentation.databinding.IncludeItemScheduleDetailBinding
import com.hugg.presentation.ui.main.calendar.viewHolder.ScheduleViewHolder

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