package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.InfoCategoryType
import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationViewHolder(
    private val binding: ItemInfoBinding,
    private val listener: InformationAdapter.InformationAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var type : InfoCategoryType

    private val informationDetailAdapter: InformationDetailAdapter by lazy {
        InformationDetailAdapter(false, object : InformationDetailAdapter.InformationDetailAdapterDelegate {
            override fun onClickCard(url: String) {
                listener.onClickUrl(url)
            }
        })
    }
    init {
        binding.infoCategoryRecyclerView.apply {
            adapter = informationDetailAdapter
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.btnSubsidyDetail.setOnClickListener {
            listener.onClickBtnDetail(type)
        }
    }

    fun bind(item : InfoCategoryListVo) {
        type = item.type
        binding.apply {
            textTitle.text = when(item.type){
                InfoCategoryType.ESSENTIAL -> root.context.getString(R.string.info_pregnancy)
                InfoCategoryType.HUGG_PICK -> root.context.getString(R.string.info_infertility)
                InfoCategoryType.NOTHING -> ""
            }
            informationDetailAdapter.submitList(item.list)
        }
    }
}