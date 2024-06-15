package com.foregg.presentation.ui.main.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoCategoryBinding
import com.foregg.presentation.databinding.ItemInfoCategoryDetailBinding

class InformationDetailAdapter(
    private val isDetail: Boolean,
    private val listener : InformationDetailAdapterDelegate
): ListAdapter<InfoItemVo, RecyclerView.ViewHolder>(InformationDiffUtilCallBack()) {

    interface InformationDetailAdapterDelegate {
        fun onClickCard(url : String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InformationDetailViewHolder -> holder.bind(currentList[position])
            is InformationCategoryDetailViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(isDetail) {
            false -> {
                val binding = ItemInfoCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InformationDetailViewHolder(binding, listener)
            }
            true -> {
                val binding = ItemInfoCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InformationCategoryDetailViewHolder(binding, listener)
            }
        }
    }
}

class InformationDiffUtilCallBack: DiffUtil.ItemCallback<InfoItemVo>() {
    override fun areContentsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem.url == newItem.url
    }
}