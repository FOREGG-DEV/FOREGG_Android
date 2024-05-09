package com.foregg.presentation.ui.main.profile.myMedicineInjection.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemMedicineInjectionBinding
import com.foregg.presentation.util.TimeFormatter
import kotlin.properties.Delegates

class MedicineInjectionViewHolder(
    private val binding: IncludeItemMedicineInjectionBinding,
    private val listener: MedicineInjectionCardAdapter.MedicineInjectionCardDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var id by Delegates.notNull<Long>()

    init {
        binding.apply {
            root.setOnClickListener {
                listener.onClickDetail(id)
            }
        }
    }

    fun bind(item : MyMedicineInjectionResponseVo) {
        id = item.id
        binding.apply {
            if(item.date.isEmpty()) setRepeatDateText(item)
            else textDate.text = item.date
            textTitle.text = item.name
        }
    }

    private fun setRepeatDateText(item : MyMedicineInjectionResponseVo){
        binding.apply {
            val dateText = "${TimeFormatter.getDotsDate(item.startDate)} ${root.context.getString(R.string.sign_ssn_split)} ${TimeFormatter.getDotsDate(item.endDate)}"
            textDate.text = dateText
            textRepeatDate.text = item.repeatDays
        }
    }
}