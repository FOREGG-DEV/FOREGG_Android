package com.foregg.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.ItemHomeIntroductionBinding

class HomeIntroductionAdapter: ListAdapter<Int, RecyclerView.ViewHolder>(
    HomeIntroductionDiffUtilCallback()
) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HomeIntroductionViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemHomeIntroductionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeIntroductionViewHolder(binding)
    }
}

class HomeIntroductionDiffUtilCallback: DiffUtil.ItemCallback<Int>() {
    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}