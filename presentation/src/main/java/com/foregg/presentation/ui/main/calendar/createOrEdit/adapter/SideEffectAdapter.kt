package com.foregg.presentation.ui.main.calendar.createOrEdit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.SideEffectVo
import com.foregg.presentation.databinding.ItemSideEffectNoBarsBinding
import com.foregg.presentation.ui.main.calendar.createOrEdit.viewHolder.SideEffectViewHolder

class SideEffectAdapter(
) : ListAdapter<SideEffectVo, RecyclerView.ViewHolder>(SideEffectDiffCallBack()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SideEffectViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSideEffectNoBarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SideEffectViewHolder(binding)
    }
}

class SideEffectDiffCallBack : DiffUtil.ItemCallback<SideEffectVo>() {
    override fun areItemsTheSame(oldItem: SideEffectVo, newItem: SideEffectVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: SideEffectVo, newItem: SideEffectVo): Boolean = oldItem == newItem
}