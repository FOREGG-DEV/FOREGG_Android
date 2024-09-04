package com.hugg.presentation.ui.main.home.challenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hugg.domain.model.response.MyChallengeListItemVo
import com.hugg.presentation.databinding.ItemMyChallengeBinding

class MyChallengeListAdapter(
    private val listener: DeleteMyChallengeDelegate
) : ListAdapter<MyChallengeListItemVo, RecyclerView.ViewHolder> (
    MyChallengeListCallBack()
){

    interface DeleteMyChallengeDelegate {
        fun showDialog(id: Long)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MyChallengeListViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMyChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyChallengeListViewHolder(binding, parent.context, listener)
    }
}

class MyChallengeListCallBack : DiffUtil.ItemCallback<MyChallengeListItemVo>() {
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