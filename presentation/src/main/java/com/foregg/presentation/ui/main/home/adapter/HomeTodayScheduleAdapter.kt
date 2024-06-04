package com.foregg.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.databinding.ItemTodayScheduleBinding
import com.foregg.presentation.util.ForeggLog


class HomeTodayScheduleAdapter(
    private val listener: HomeTodayScheduleDelegate
) : ListAdapter<HomeRecordResponseVo, RecyclerView.ViewHolder>(
    HomeTodayScheduleDiffUtilCallBack()
) {
    private var nearestSchedulePosition: Int = -1

    interface HomeTodayScheduleDelegate {
        fun onClickRecordTreatment(id: Long, recordType: RecordType)
        fun calculateNearestSchedulePosition(list: List<HomeRecordResponseVo>): Int
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeTodayScheduleViewHolder -> holder.bind(currentList[position], position == nearestSchedulePosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTodayScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTodayScheduleViewHolder(binding, listener)
    }

    override fun submitList(list: MutableList<HomeRecordResponseVo>?) {
        super.submitList(list)
        list?.let { nearestSchedulePosition = listener.calculateNearestSchedulePosition(list) }
    }
}

class HomeTodayScheduleDiffUtilCallBack: DiffUtil.ItemCallback<HomeRecordResponseVo>() {
    override fun areItemsTheSame(
        oldItem: HomeRecordResponseVo,
        newItem: HomeRecordResponseVo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HomeRecordResponseVo,
        newItem: HomeRecordResponseVo
    ): Boolean {
        return oldItem == newItem
    }
}