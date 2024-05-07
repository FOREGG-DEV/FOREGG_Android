package com.foregg.presentation.ui.main.calendar.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemScheduleDetailBinding
import com.foregg.presentation.ui.main.calendar.adapter.ScheduleAdapter
import kotlin.properties.Delegates

class ScheduleViewHolder(
    private val binding: IncludeItemScheduleDetailBinding,
    private val listener: ScheduleAdapter.ScheduleDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var id by Delegates.notNull<Long>()
    private lateinit var type : RecordType

    init {
        binding.apply {
            root.setOnClickListener {
                listener.onClickDetail(id, type)
            }

            imgBtnDelete.setOnClickListener {
                listener.onClickDelete(id)
            }
        }
    }

    fun bind(item : ScheduleDetailVo) {
        id = item.id
        type = item.recordType
        binding.apply {
            setBackGround(item.recordType)
            textType.text = item.recordType.type
            textTime.text = item.repeatTimes.first().time
            textTitle.text = item.name
        }
    }

    private fun setBackGround(type : RecordType){
        val background = when(type){
            RecordType.MEDICINE -> R.drawable.bg_rectangle_filled_medicine_radius_4
            RecordType.INJECTION -> R.drawable.bg_rectangle_filled_injection_radius_4
            RecordType.HOSPITAL -> R.drawable.bg_rectangle_filled_hospital_radius_4
            RecordType.ETC -> R.drawable.bg_rectangle_filled_etc_radius_4
        }
        binding.constraintLayoutType.setBackgroundResource(background)
    }
}