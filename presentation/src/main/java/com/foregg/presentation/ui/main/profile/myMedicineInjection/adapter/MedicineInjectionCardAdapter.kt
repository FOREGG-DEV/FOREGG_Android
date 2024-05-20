package com.foregg.presentation.ui.main.profile.myMedicineInjection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.presentation.databinding.IncludeItemMedicineInjectionBinding

class MedicineInjectionCardAdapter(
    private val listener : MedicineInjectionCardDelegate
) : ListAdapter<MyMedicineInjectionResponseVo, RecyclerView.ViewHolder>(MedicineInjectionCardDiffCallBack()) {

    interface MedicineInjectionCardDelegate {
        fun onClickDetail(id : Long)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MedicineInjectionViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMedicineInjectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineInjectionViewHolder(binding, listener)
    }
}

class MedicineInjectionCardDiffCallBack : DiffUtil.ItemCallback<MyMedicineInjectionResponseVo>() {
    override fun areItemsTheSame(oldItem: MyMedicineInjectionResponseVo, newItem: MyMedicineInjectionResponseVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MyMedicineInjectionResponseVo, newItem: MyMedicineInjectionResponseVo): Boolean = oldItem == newItem
}