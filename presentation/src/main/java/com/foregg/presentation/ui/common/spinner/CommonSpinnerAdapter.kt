package com.foregg.presentation.ui.common.spinner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.IncludeItemSurgeryTypeBinding

class CommonSpinnerAdapter(
    private val listener : CommonSpinnerDelegate
) : ListAdapter<String, RecyclerView.ViewHolder>(CommonSpinnerDiffCallBack()) {

    interface CommonSpinnerDelegate{
        fun onClickType(type: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommonSpinnerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemSurgeryTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommonSpinnerViewHolder(binding, listener)
    }
}

class CommonSpinnerDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}