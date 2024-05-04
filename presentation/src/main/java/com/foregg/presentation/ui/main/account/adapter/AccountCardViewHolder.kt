package com.foregg.presentation.ui.main.account.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.AccountType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.vo.AccountCardVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemAccountCardBinding
import com.foregg.presentation.util.ForeggLog
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates

class AccountCardViewHolder(
    private val binding: IncludeItemAccountCardBinding,
    private val listener: AccountCardAdapter.AccountCardDelegate,
    private val adapter: AccountCardAdapter
) : RecyclerView.ViewHolder(binding.root) {

    private var id by Delegates.notNull<Long>()
    private lateinit var type : RecordType

    init {
        binding.apply {
            root.setOnClickListener {
                ForeggLog.D("그냥클릭")
            }

            root.setOnLongClickListener {
                ForeggLog.D("롱클릭")
                return@setOnLongClickListener(true)
            }
        }
    }

    fun bind(item : AccountCardVo) {
        id = item.id
        binding.apply {
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
                AccountType.PERSONAL_EXPEND -> {
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