package com.foregg.presentation.ui.main.information.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationAdapter(
    private var data: Map<String, List<InfoItemVo>>,
    private val context: Context,
    private val listener: InformationAdapterDelegate
): RecyclerView.Adapter<InformationViewHolder>() {

    interface InformationAdapterDelegate {
        fun onClickBtnDetail(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationViewHolder(binding, context, listener)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val title = data.keys.toList() [position]
        val items = data[title] ?: emptyList()
        holder.bind(title, items, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: Map<String, List<InfoItemVo>>) {
        this.data = newData
        notifyDataSetChanged()
    }
}

