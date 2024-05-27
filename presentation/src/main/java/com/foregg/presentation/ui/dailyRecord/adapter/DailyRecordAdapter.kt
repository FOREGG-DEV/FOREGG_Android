package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.databinding.ItemDailyRecordBinding

class DailyRecordAdapter : ListAdapter<DailyRecordResponseItemVo, RecyclerView.ViewHolder> (
    DailyRecordListDiffCallBack()
) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DailyRecordViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDailyRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyRecordViewHolder(binding)
    }
}

class DailyRecordListDiffCallBack : DiffUtil.ItemCallback<DailyRecordResponseItemVo>() {
    override fun areContentsTheSame(
        oldItem: DailyRecordResponseItemVo,
        newItem: DailyRecordResponseItemVo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: DailyRecordResponseItemVo,
        newItem: DailyRecordResponseItemVo
    ): Boolean {
        return oldItem.id == newItem.id
    }
}