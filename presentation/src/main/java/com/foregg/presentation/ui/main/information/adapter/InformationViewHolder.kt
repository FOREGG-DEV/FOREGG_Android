package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationViewHolder(
    private val binding: ItemInfoBinding,
    private val listener: InformationAdapter.InformationAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var type : InformationType

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
                InformationType.ESSENTIAL -> root.context.getString(R.string.info_pregnancy)
                InformationType.HUGG_PICK -> root.context.getString(R.string.info_infertility)
                InformationType.NOTHING -> ""
            }
            informationDetailAdapter.submitList(item.list)
        }
    }
}