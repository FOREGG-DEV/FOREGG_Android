package com.foregg.presentation.ui.main.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemTodayScheduleBinding
import com.foregg.domain.model.enums.RecordType
import com.foregg.presentation.util.UserInfo
import org.threeten.bp.LocalTime

class HomeTodayScheduleViewHolder(
    private val binding: ItemTodayScheduleBinding,
    private val listener: HomeTodayScheduleAdapter.HomeTodayScheduleDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var id: Long? = null
    private var recordType: RecordType = RecordType.ETC

    init {
        binding.btnRecordTreatment.setOnClickListener {
            id?.let { id ->
                listener.onClickRecordTreatment(id, recordType)
            }
        }

        binding.btnEditSchedule.setOnClickListener {
            id?.let { id ->
                listener.onClickRecordTreatment(id, recordType)
            }
        }
    }

    fun bind(item: HomeRecordResponseVo, isNearestSchedule: Boolean) {
        binding.apply {
            id = item.id
            recordType = item.recordType

            if (isNearestSchedule) {
                cardView.strokeWidth = 2
                cardView.elevation = 2F
            }

            scheduleTextContent.text = item.recordType.type
            scheduleNameText.text = item.name
            scheduleTimeText.text = item.times.first()

            if (item.memo.isBlank() || item.recordType == RecordType.HOSPITAL) {
                scheduleMemoText.visibility = View.GONE
            } else {
                scheduleMemoText.text = item.memo
            }

            when(item.recordType) {
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
                    if (UserInfo.info.genderType == GenderType.FEMALE) btnRecordTreatment.visibility = View.VISIBLE
                }
                RecordType.ETC -> {
                    scheduleContentLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_personal_radius_4)
                    btnRecordTreatment.visibility = View.GONE
                }
            }
        }
    }
}