package com.foregg.presentation.ui.main.information.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationViewHolder(
    private val binding: ItemInfoBinding,
    private val context: Context,
    private val listener: InformationAdapter.InformationAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {
    var position: Int? = null

    init {
        binding.btnSubsidyDetail.setOnClickListener {
            position?.let {
                listener.onClickBtnDetail(it)
            }
        }
    }

    fun bind(title: String, items: List<InfoItemVo>, position: Int) {
        this.position = position
        val informationDetailAdapter = InformationDetailAdapter(false)
        binding.apply {
            textTitle.text = title
            infoCategoryRecyclerView.adapter = informationDetailAdapter
            infoCategoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            informationDetailAdapter.submitList(items)
        }
    }
}