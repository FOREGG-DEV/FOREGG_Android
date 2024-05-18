package com.foregg.presentation.ui.main.home.challenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.presentation.databinding.ItemChallengeBinding
import com.foregg.presentation.ui.main.home.challenge.ChallengeViewModel

class ChallengeListAdapter : ListAdapter<ChallengeCardVo, RecyclerView.ViewHolder> (
    ChallengeListDiffCallBack()
) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChallengeListViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeListViewHolder(binding, parent.context)
    }
}

class ChallengeListDiffCallBack : DiffUtil.ItemCallback<ChallengeCardVo>() {
    override fun areContentsTheSame(oldItem: ChallengeCardVo, newItem: ChallengeCardVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: ChallengeCardVo, newItem: ChallengeCardVo): Boolean {
        return oldItem == newItem
    }
}