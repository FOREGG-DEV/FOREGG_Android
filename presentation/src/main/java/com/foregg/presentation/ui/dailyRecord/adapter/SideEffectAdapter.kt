package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.presentation.databinding.ItemSideEffectBinding

class SideEffectAdapter: ListAdapter<SideEffectListItemVo, RecyclerView.ViewHolder>(
    SideEffectListDiffCallBack()
) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SideEffectViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSideEffectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SideEffectViewHolder(binding)
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