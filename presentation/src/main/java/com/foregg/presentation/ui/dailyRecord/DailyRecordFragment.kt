package com.foregg.presentation.ui.dailyRecord

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentDailyRecordBinding
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordAdapter
import com.foregg.presentation.util.ForeggLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyRecordFragment : BaseFragment<FragmentDailyRecordBinding, DailyRecordPageState, DailyRecordViewModel>(
    FragmentDailyRecordBinding::inflate
) {
    private val dailyRecordAdapter = DailyRecordAdapter()

    override val viewModel: DailyRecordViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
            recordRecyclerView.adapter = dailyRecordAdapter
            recordRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        bindTab()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.dailyRecordList.collect {
                    dailyRecordAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as DailyRecordEvent)
                }
            }
        }
    }

    private fun bindTab() {
        binding.apply {
            customTabBar.leftTab.setOnClickListener {
                customTabBar.leftBtnClicked { viewModel.updateTabType(DailyRecordTabType.ADVERSE_EFFECT) }
            }
            customTabBar.rightTab.setOnClickListener {
                customTabBar.rightBtnClicked { viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD) }
                binding.recordRecyclerView.adapter = dailyRecordAdapter
            }
        }
    }

    private fun sortEvent(event: DailyRecordEvent) {
        when(event) {
            DailyRecordEvent.GoToCreateDailyRecordEvent -> goToCreateDailyRecord()
        }
    }

    private fun goToCreateDailyRecord() {
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateDailyRecord()
        findNavController().navigate(action)
    }
}