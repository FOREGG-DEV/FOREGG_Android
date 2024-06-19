package com.foregg.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.HomeAdCardType
import com.foregg.domain.model.vo.home.HomeAdCardVo
import com.foregg.presentation.databinding.ItemHomeIntroductionBinding

class HomeIntroductionAdapter(
    private val listener : HomeIntroductionDelegate
): ListAdapter<HomeAdCardVo, RecyclerView.ViewHolder>(
    HomeIntroductionDiffUtilCallback()
) {

    interface HomeIntroductionDelegate {
        fun onClickCard(type : HomeAdCardType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HomeIntroductionViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemHomeIntroductionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeIntroductionViewHolder(binding, listener)
    }
}

class HomeIntroductionDiffUtilCallback: DiffUtil.ItemCallback<HomeAdCardVo>() {
    override fun areContentsTheSame(oldItem: HomeAdCardVo, newItem: HomeAdCardVo): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: HomeAdCardVo, newItem: HomeAdCardVo): Boolean {
        return oldItem.type == newItem.type
    }
}