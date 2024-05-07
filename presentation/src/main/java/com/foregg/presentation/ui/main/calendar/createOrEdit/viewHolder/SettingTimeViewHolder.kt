package com.foregg.presentation.ui.main.calendar.createOrEdit.viewHolder

import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemSettingScheduleTimeBinding
import com.foregg.presentation.ui.main.calendar.createOrEdit.adapter.SettingTimeAdapter

class SettingTimeViewHolder(
    private val binding: IncludeItemSettingScheduleTimeBinding,
    private val adapter: SettingTimeAdapter
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val MAX_HOUR = "12"
        const val MAX_AM_HOUR = "11"
        const val MAX_MINUTE = "59"
    }

    private var position : Int = -1
    private var isLast : Boolean = false

    init {
        binding.apply {
            constraintLayoutTimeType.setOnClickListener {
                textTimeApPm.text = if(textTimeApPm.text == root.context.getString(R.string.calendar_create_edit_time_am)) root.context.getString(R.string.calendar_create_edit_time_pm)
                                else root.context.getString(R.string.calendar_create_edit_time_am)
                updateAmPmBackGround()
                updateTime()
            }
            editTextHourTime.addTextChangedListener {
                when(textTimeApPm.text){
                    root.context.getString(R.string.calendar_create_edit_time_am) -> {
                        if(editTextHourTime.text.toString().isNotEmpty() && editTextHourTime.text.toString().toInt() > MAX_AM_HOUR.toInt()) editTextHourTime.setText(MAX_AM_HOUR)
                    }
                    root.context.getString(R.string.calendar_create_edit_time_pm) -> {
                        if(editTextHourTime.text.toString().isNotEmpty() && editTextHourTime.text.toString().toInt() > MAX_HOUR.toInt()) editTextHourTime.setText(MAX_HOUR)
                    }
                }
                updateTime()
            }
            editTextMinuteTime.addTextChangedListener {
                if(editTextMinuteTime.text.toString().isNotEmpty() && editTextMinuteTime.text.toString().toInt() > MAX_MINUTE.toInt()) editTextMinuteTime.setText(MAX_MINUTE)
                updateTime()
            }
            constraintLayoutAddOrMinus.setOnClickListener {
                if(isLast) adapter.addItem() else adapter.removeItem(position)
            }
        }
    }

    private fun updateTime(){
        binding.apply {
            val time = CreateScheduleTimeVo(textTimeApPm.text.toString(), editTextHourTime.text.toString(), editTextMinuteTime.text.toString())
            adapter.updateItem(position, time)
        }
    }

    fun bind(item : CreateScheduleTimeVo, position : Int, isLast : Boolean) {
        this.position = position
        this.isLast = isLast
        binding.apply {
            textTimeApPm.text = item.amOrPm.ifEmpty { root.context.getString(R.string.calendar_create_edit_time_am) }
            editTextHourTime.setText(item.hour)
            editTextMinuteTime.setText(item.minute)
            val image = if(isLast) R.drawable.ic_plus else R.drawable.ic_minus
            imgPlusMinus.setBackgroundResource(image)
            updateAmPmBackGround()
        }
    }

    private fun updateAmPmBackGround(){
        binding.apply {
            if(textTimeApPm.text == root.context.getString(R.string.calendar_create_edit_time_am)){
                constraintLayoutTimeType.setBackgroundResource(R.drawable.bg_rectangle_filled_gs_10_radius_4)
                textTimeApPm.setTextColor(ContextCompat.getColor(root.context, R.color.gs_90))
            }
            else{
                constraintLayoutTimeType.setBackgroundResource(R.drawable.bg_rectangle_filled_gs_80_radius_4)
                textTimeApPm.setTextColor(ContextCompat.getColor(root.context, R.color.gs_10))
            }
        }
    }
}