package com.foregg.presentation.ui.main.calendar.createOrEdit.viewHolder

import android.app.TimePickerDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemSettingScheduleTimeBinding
import com.foregg.presentation.ui.main.calendar.createOrEdit.adapter.SettingTimeAdapter
import java.util.Calendar

class SettingTimeViewHolder(
    private val binding: IncludeItemSettingScheduleTimeBinding,
    private val adapter: SettingTimeAdapter
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val MAX_HOUR = 12
    }

    private var position : Int = -1
    private var isLast : Boolean = false

    private val calendar = Calendar.getInstance()
    private val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        bindText(hour, minute)
    }
    private val timePickerDialog : TimePickerDialog by lazy { TimePickerDialog(binding.root.context,
        R.style.DatePickerStyle,
        timePickerListener,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false) }

    init {
        binding.apply {
            constraintLayoutTime.setOnClickListener {
                showTimePicker()
            }
            imgBtnClock.setOnClickListener {
                showTimePicker()
            }
            constraintLayoutAddOrMinus.setOnClickListener {
                if(isLast) adapter.addItem() else adapter.removeItem(position)
            }
        }
    }

    fun bind(item : CreateScheduleTimeVo, position : Int, isLast : Boolean) {
        this.position = position
        this.isLast = isLast
        binding.apply {
            textTime.text = if(item.isEmpty()) "" else String.format("%s %d:%02d", item.amOrPm, item.hour.toInt(), item.minute.toInt())
            val image = if(isLast) R.drawable.ic_plus else R.drawable.ic_minus
            imgPlusMinus.setBackgroundResource(image)
        }
    }

    private fun showTimePicker(){
        timePickerDialog.show()
        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(binding.root.context, R.color.main))
        timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(binding.root.context, R.color.main))
    }

    private fun bindText(hour : Int, minute : Int){
        binding.apply {
            val amPm = if (hour < MAX_HOUR) root.context.getString(R.string.calendar_create_edit_time_am) else root.context.getString(R.string.calendar_create_edit_time_pm)
            val hourTime = if (hour % MAX_HOUR == 0 && amPm == root.context.getString(R.string.calendar_create_edit_time_pm)) MAX_HOUR else hour % MAX_HOUR
            textTime.text = String.format("%s %d:%02d", amPm, hourTime, minute)
            updateTime(amPm, hourTime.toString(), minute.toString())
        }
    }

    private fun updateTime(amPm : String, hour : String, minute : String){
        binding.apply {
            val time = CreateScheduleTimeVo(amPm, hour, minute)
            adapter.updateItem(position, time)
        }
    }
}