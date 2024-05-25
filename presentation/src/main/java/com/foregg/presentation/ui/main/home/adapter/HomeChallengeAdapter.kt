package com.foregg.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.databinding.ItemHomeMyChallengeBinding

class HomeChallengeAdapter(
    private val listener: HomeChallengeDelegate
) : ListAdapter<MyChallengeListItemVo, RecyclerView.ViewHolder>(
    HomeChallengeDiffUtilCallBack()
) {
    interface HomeChallengeDelegate {
        fun showDialog(id: Long)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HomeChallengeViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemHomeMyChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeChallengeViewHolder(binding, listener)
    }
}

class HomeChallengeDiffUtilCallBack: DiffUtil.ItemCallback<MyChallengeListItemVo>() {
    override fun areContentsTheSame(
        oldItem: MyChallengeListItemVo,
        newItem: MyChallengeListItemVo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: MyChallengeListItemVo,
        newItem: MyChallengeListItemVo
    ): Boolean {
        return oldItem.id == newItem.id
    }
}