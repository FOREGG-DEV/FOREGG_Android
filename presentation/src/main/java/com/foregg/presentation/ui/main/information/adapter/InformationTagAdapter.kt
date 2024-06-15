package com.foregg.presentation.ui.main.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.IncludeItemInformationTagBinding

class InformationTagAdapter(
    private val isDetail: Boolean,
    private val listener : InformationTagDelegate
) : ListAdapter<String, RecyclerView.ViewHolder>(InformationTextDiffCallBack()) {

    interface InformationTagDelegate {
        fun onClick()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InformationTagViewHolder -> holder.bind(currentList[position], isDetail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemInformationTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationTagViewHolder(binding, listener)
    }
}

class InformationTextDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}
