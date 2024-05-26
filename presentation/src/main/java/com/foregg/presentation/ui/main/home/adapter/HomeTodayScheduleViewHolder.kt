package com.foregg.presentation.ui.main.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemTodayScheduleBinding
import com.foregg.domain.model.enums.RecordType

class HomeTodayScheduleViewHolder(
    private val binding: ItemTodayScheduleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeRecordResponseVo) {
        binding.apply {
            val recordType = item.recordType

            scheduleTextContent.text = recordType.type
            scheduleNameText.text = item.name
            scheduleTimeText.text = item.times.first()

            if (item.memo.isBlank()) {
                scheduleMemoText.visibility = View.GONE
            } else {
                scheduleMemoText.text = item.memo
            }

            when(recordType) {
                RecordType.MEDICINE -> {
                    scheduleContentLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_medicine_radius_4)
                    btnRecordTreatment.visibility = View.GONE
                }
                RecordType.INJECTION -> {
                    scheduleContentLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_injection_radius_4)
                    btnRecordTreatment.visibility = View.GONE
                }
                RecordType.HOSPITAL -> {
                    scheduleContentLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_hospital_radius_4)
                    btnRecordTreatment.visibility = View.VISIBLE
                }
                RecordType.ETC -> {
                    scheduleContentLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_personal_radius_4)
                    btnRecordTreatment.visibility = View.GONE
                }
            }
        }
    }
}