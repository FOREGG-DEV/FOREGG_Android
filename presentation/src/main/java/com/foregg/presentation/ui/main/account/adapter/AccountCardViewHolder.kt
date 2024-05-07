package com.foregg.presentation.ui.main.account.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.AccountType
import com.foregg.domain.model.vo.AccountCardVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemAccountCardBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates

class AccountCardViewHolder(
    private val binding: IncludeItemAccountCardBinding,
    private val listener: AccountCardAdapter.AccountCardDelegate,
    private val adapter: AccountCardAdapter
) : RecyclerView.ViewHolder(binding.root) {

    private var id by Delegates.notNull<Long>()
    private lateinit var vo : AccountCardVo

    init {
        binding.apply {
            constraintLayoutCard.setOnClickListener {
                //여러개면 if(adapter.getSelectMode()) listener.onSelectItem(vo.copy(isSelected = !vo.isSelected)) 이걸로 변경
                if(vo.isSelected) listener.onSelectItem(vo.copy(isSelected = false))
                else listener.onClickItem(vo)
            }

            constraintLayoutCard.setOnLongClickListener {
                if(adapter.getSelectMode()) return@setOnLongClickListener(false)
                adapter.changeMode()
                listener.onSelectItem(vo.copy(isSelected = !vo.isSelected))
                return@setOnLongClickListener(true)
            }
        }
    }

    fun bind(item : AccountCardVo) {
        id = item.id
        vo = item
        binding.apply {
            val res = if(item.isSelected) R.drawable.bg_rectangle_filled_gs_10_radius_8 else R.drawable.bg_rectangle_filled_white_radius_8
            constraintLayoutCard.setBackgroundResource(res)
            textDate.text = item.date
            textRound.text = root.context.getString(R.string.account_round_unit, item.round)
            setType(item.type)
            textTitle.text = item.title
            textMoney.text = getMoneyFormat(item.money)
        }
    }

    private fun setType(type : AccountType){
        binding.apply {
            when(type){
                AccountType.PERSONAL_EXPENSE -> {
                    constraintLayoutType.setBackgroundResource(R.drawable.bg_rectangle_filled_medicine_radius_4)
                    textType.text = root.context.getString(R.string.account_personal)
                    textSmallType.text = root.context.getString(R.string.account_personal)
                }
                AccountType.SUBSIDY -> {
                    constraintLayoutType.setBackgroundResource(R.drawable.bg_rectangle_filled_injection_radius_4)
                    textType.text = root.context.getString(R.string.account_subsidy_type)
                    textSmallType.text = root.context.getString(R.string.account_subsidy_type)
                }
            }
        }
    }

    private fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return binding.root.context.getString(R.string.account_money_unit, koreanFormat.format(money))
    }
}