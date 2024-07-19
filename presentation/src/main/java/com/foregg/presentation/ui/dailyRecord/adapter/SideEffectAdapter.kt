package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.databinding.ItemSideEffectBinding

class SideEffectAdapter(
    private val listener : SideEffectDelegate
): ListAdapter<SideEffectListItemVo, RecyclerView.ViewHolder>(
    SideEffectListDiffCallBack()
) {

    interface SideEffectDelegate {
        fun onLongClickSideEffect(item: SideEffectListItemVo)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SideEffectViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSideEffectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SideEffectViewHolder(binding, listener)
    }

    fun getItemPosition(item: SideEffectListItemVo): Int {
        return currentList.indexOf(item)
    }
}

class SideEffectListDiffCallBack: DiffUtil.ItemCallback<SideEffectListItemVo>() {
    override fun areContentsTheSame(
        oldItem: SideEffectListItemVo,
        newItem: SideEffectListItemVo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: SideEffectListItemVo,
        newItem: SideEffectListItemVo
    ): Boolean {
        return oldItem.id == newItem.id
    }
}