package com.foregg.presentation.ui.main.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.account.AccountCardVo
import com.foregg.presentation.databinding.IncludeItemAccountCardBinding

class AccountCardAdapter(
    private val listener : AccountCardDelegate
) : ListAdapter<AccountCardVo, RecyclerView.ViewHolder>(AccountCardDiffCallBack()) {

    private var selectMode = false

    interface AccountCardDelegate {
        fun onClickItem(vo: AccountCardVo)
        fun onSelectItem(vo: AccountCardVo)
        fun changeMode(selectMode : Boolean)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AccountCardViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemAccountCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountCardViewHolder(binding, listener, this)
    }

    fun getSelectMode() : Boolean = selectMode
    fun changeMode() {
        selectMode = !selectMode
        listener.changeMode(selectMode)
    }
}

class AccountCardDiffCallBack : DiffUtil.ItemCallback<AccountCardVo>() {
    override fun areItemsTheSame(oldItem: AccountCardVo, newItem: AccountCardVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: AccountCardVo, newItem: AccountCardVo): Boolean = oldItem == newItem
}