package com.foregg.presentation.ui.main.information.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding
import com.foregg.presentation.databinding.ItemInfoCategoryBinding
import com.foregg.presentation.databinding.ItemInfoCategoryDetailBinding

class InformationDetailAdapter(
    private val isDetail: Boolean
): ListAdapter<InfoItemVo, RecyclerView.ViewHolder>(
    InformationDiffUtilCallBack()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InformationDetailViewHolder -> holder.bind(currentList[position])
            is InformationCategoryDetailViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(isDetail) {
            false -> {
                val binding = ItemInfoCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InformationDetailViewHolder(binding)
            }
            true -> {
                val binding = ItemInfoCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InformationCategoryDetailViewHolder(binding)
            }
        }
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        if (position >= 0) {
            val column = position % spanCount
            outRect.apply {
                left = spacing - column * spacing / spanCount
                right = if (column == 2) 0
                else (column + 1) * spacing / spanCount
                if (position < spanCount) top = spacing
                bottom = spacing
            }
        } else {
            outRect.apply {
                left = 0
                right = 0
                top = 0
                bottom = 0
            }
        }
    }
}

class InformationDiffUtilCallBack: DiffUtil.ItemCallback<InfoItemVo>() {
    override fun areContentsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: InfoItemVo, newItem: InfoItemVo): Boolean {
        return oldItem == newItem
    }
}