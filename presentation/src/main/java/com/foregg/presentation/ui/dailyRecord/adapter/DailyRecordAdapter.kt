package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.databinding.ItemDailyRecordBinding

class DailyRecordAdapter(
    private val listener: DailyRecordDelegate
) : ListAdapter<DailyRecordResponseItemVo, RecyclerView.ViewHolder> (
    DailyRecordListDiffCallBack()
) {

    interface DailyRecordDelegate {
        fun onClickEmotion(request: PutEmotionVo)
        fun onClickDailyRecord(item: DailyRecordResponseItemVo)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DailyRecordViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDailyRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyRecordViewHolder(binding, listener)
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