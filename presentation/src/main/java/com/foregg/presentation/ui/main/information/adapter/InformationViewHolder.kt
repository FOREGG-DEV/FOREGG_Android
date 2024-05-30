package com.foregg.presentation.ui.main.information.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationViewHolder(
    private val binding: ItemInfoBinding,
    private val context: Context,
    private val listener: InformationAdapter.InformationAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnSubsidyDetail.setOnClickListener {
            listener.onClickBtnSubsidyDetail()
        }
    }

    fun bind(title: String, items: List<InfoItemVo>) {
        val informationDetailAdapter = InformationDetailAdapter()
        binding.apply {
            textTitle.text = title
            infoCategoryRecyclerView.adapter = informationDetailAdapter
            infoCategoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            informationDetailAdapter.submitList(items)

            if (title == context.getString(R.string.info_possible_subsidy)) btnSubsidyDetail.visibility = View.VISIBLE
            else btnSubsidyDetail.visibility = View.GONE
        }
    }
}