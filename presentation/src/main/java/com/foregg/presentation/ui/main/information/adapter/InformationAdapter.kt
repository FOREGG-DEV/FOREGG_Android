package com.foregg.presentation.ui.main.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationAdapter: ListAdapter<InfoItemVo, RecyclerView.ViewHolder>(
    InformationDiffUtilCallBack()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InformationViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationViewHolder(binding)
    }
}

class InformationDiffUtilCallBack: DiffUtil.ItemCallback<InfoItemVo>() {
    override fun areContentsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem == newItem
    }
}