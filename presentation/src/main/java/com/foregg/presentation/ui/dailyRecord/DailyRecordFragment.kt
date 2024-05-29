package com.foregg.presentation.ui.dailyRecord

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.enums.NotificationType
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentDailyRecordBinding
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordAdapter
import com.foregg.presentation.ui.dailyRecord.adapter.SideEffectAdapter
import com.foregg.presentation.util.ForeggNotification
import com.foregg.presentation.util.PendingExtraValue
import com.foregg.presentation.util.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyRecordFragment : BaseFragment<FragmentDailyRecordBinding, DailyRecordPageState, DailyRecordViewModel>(
    FragmentDailyRecordBinding::inflate
) {
    private val dailyRecordAdapter = DailyRecordAdapter()
    private val sideEffectAdapter = SideEffectAdapter()

    override val viewModel: DailyRecordViewModel by viewModels()
    override fun initView() {
        if(UserInfo.info.genderType == GenderType.MALE) ForeggNotification.updateNoty(requireContext(), NotificationType.TODAY_RECORD_MALE, false)
        binding.apply {
            vm = viewModel
            recordRecyclerView.adapter = sideEffectAdapter
            recordRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        viewModel.setView()
        bindTab()
        checkFcm()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.dailyRecordList.collect {
                    dailyRecordAdapter.submitList(it.reversed())
                }
            }

            launch {
                viewModel.uiState.sideEffectList.collect {
                    sideEffectAdapter.submitList(it.reversed())
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
            customTabBar.leftBtnClicked {
                viewModel.updateTabType(DailyRecordTabType.ADVERSE_EFFECT)
                recordRecyclerView.adapter = sideEffectAdapter
            }
            customTabBar.rightBtnClicked {
                viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD)
                recordRecyclerView.adapter = dailyRecordAdapter
            }
        }
    }

    private fun sortEvent(event: DailyRecordEvent) {
        when(event) {
            DailyRecordEvent.GoToCreateDailyRecordEvent -> goToCreateDailyRecord()
            DailyRecordEvent.OnClickBtnClose -> findNavController().popBackStack()
        }
    }

    private fun goToCreateDailyRecord() {
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateDailyRecord()
        findNavController().navigate(action)
    }

    private fun checkFcm(){
        if(requireActivity().intent.getStringExtra(PendingExtraValue.KEY) == PendingExtraValue.TODAY_RECORD_MALE) {
            binding.customTabBar.setRightBtnClickedBackground()
            viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD)
            binding.recordRecyclerView.adapter = dailyRecordAdapter
        }
    }
}