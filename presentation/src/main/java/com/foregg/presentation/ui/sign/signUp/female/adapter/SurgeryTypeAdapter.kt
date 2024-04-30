package com.foregg.presentation.ui.sign.signUp.female.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.databinding.IncludeItemSurgeryTypeBinding

class SurgeryTypeAdapter(
    private val listener : SurgeryTypeDelegate
) : ListAdapter<SurgeryType, RecyclerView.ViewHolder>(SurgeryTypeDiffCallBack()) {

    interface SurgeryTypeDelegate{
        fun onClickType(type: SurgeryType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SurgeryTypeViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemSurgeryTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SurgeryTypeViewHolder(binding, listener)
    }
}

class SurgeryTypeDiffCallBack : DiffUtil.ItemCallback<SurgeryType>() {
    override fun areItemsTheSame(oldItem: SurgeryType, newItem: SurgeryType): Boolean = oldItem.type == newItem.type
    override fun areContentsTheSame(oldItem: SurgeryType, newItem: SurgeryType): Boolean = oldItem == newItem
}