package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoCategoryBinding
import com.foregg.presentation.databinding.ItemInfoCategoryDetailBinding
import com.google.android.flexbox.FlexboxLayoutManager

class InformationDetailViewHolder(
    private val binding: ItemInfoCategoryBinding,
    private val listener: InformationDetailAdapter.InformationDetailAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var url : String

    private val informationTagAdapter: InformationTagAdapter by lazy {
        InformationTagAdapter(false, object : InformationTagAdapter.InformationTagDelegate {
            override fun onClick() {
                listener.onClickCard(url)
            }
        })
    }
    init {
        binding.recyclerViewTag.apply {
            adapter = informationTagAdapter
            layoutManager = FlexboxLayoutManager(context)
        }

        binding.root.setOnClickListener {
            listener.onClickCard(url)
        }
    }

    fun bind(item: InfoItemVo) {
        url = item.url
        binding.apply {
            informationTagAdapter.submitList(item.tags)
            Glide.with(itemView.context)
                .load(item.image)
                .into(imgDetail)
        }
    }
}

class InformationCategoryDetailViewHolder(
    private val binding: ItemInfoCategoryDetailBinding,
    private val listener: InformationDetailAdapter.InformationDetailAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var url : String

    private val informationTagAdapter: InformationTagAdapter by lazy {
        InformationTagAdapter(false, object : InformationTagAdapter.InformationTagDelegate {
            override fun onClick() {
                listener.onClickCard(url)
            }
        })
    }

    init {
        binding.recyclerViewTag.apply {
            adapter = informationTagAdapter
            layoutManager = FlexboxLayoutManager(context)
        }

        binding.imgDetail.setOnClickListener {
            listener.onClickCard(url)
        }
    }

    fun bind(item: InfoItemVo) {
        url = item.url
        binding.apply {
            informationTagAdapter.submitList(item.tags)
            Glide.with(itemView.context)
                .load(item.image)
                .into(imgDetail)
        }
    }
}