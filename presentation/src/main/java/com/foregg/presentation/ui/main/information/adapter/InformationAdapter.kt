package com.foregg.presentation.ui.main.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.InfoCategoryType
import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationAdapter(
    private val listener : InformationAdapterDelegate
) : ListAdapter<InfoCategoryListVo, RecyclerView.ViewHolder>(InformationDiffCallBack()) {

    interface InformationAdapterDelegate {
        fun onClickBtnDetail(type : InfoCategoryType)
        fun onClickUrl(url : String)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InformationViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationViewHolder(binding, listener)
    }
}

class InformationDiffCallBack : DiffUtil.ItemCallback<InfoCategoryListVo>() {
    override fun areItemsTheSame(oldItem: InfoCategoryListVo, newItem: InfoCategoryListVo): Boolean {
        return oldItem.type == newItem.type
    }
    override fun areContentsTheSame(oldItem: InfoCategoryListVo, newItem: InfoCategoryListVo): Boolean = oldItem == newItem
}

