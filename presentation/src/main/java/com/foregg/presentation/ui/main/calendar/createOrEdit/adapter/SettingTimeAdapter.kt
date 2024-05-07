package com.foregg.presentation.ui.main.calendar.createOrEdit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.presentation.databinding.IncludeItemSettingScheduleTimeBinding
import com.foregg.presentation.ui.main.calendar.createOrEdit.viewHolder.SettingTimeViewHolder

class SettingTimeAdapter(
    private val listener : SettingTimeDelegate
): RecyclerView.Adapter<SettingTimeViewHolder>() {

    private val itemList: MutableList<CreateScheduleTimeVo> = mutableListOf()
    interface SettingTimeDelegate {
        fun updateItemList(list : List<CreateScheduleTimeVo>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingTimeViewHolder {
        val binding = IncludeItemSettingScheduleTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingTimeViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: SettingTimeViewHolder, position: Int) {
        holder.bind(itemList[position], position, position + 1 == itemCount)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(items: List<CreateScheduleTimeVo>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem() {
        itemList.add(CreateScheduleTimeVo())
        notifyDataSetChanged()
        updateItemToListener()
    }

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyDataSetChanged()
        updateItemToListener()
    }

    fun updateItem(position: Int, newItem: CreateScheduleTimeVo) {
        itemList[position] = newItem
        updateItemToListener()
    }

    private fun updateItemToListener(){
        if(itemList.any { it.hour.isEmpty() || it.minute.isEmpty() }) return
        listener.updateItemList(itemList)
    }
}