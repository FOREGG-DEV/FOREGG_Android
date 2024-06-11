package com.foregg.presentation.ui.dailyRecord

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.enums.NotificationType
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentDailyRecordBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordAdapter
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordViewHolder
import com.foregg.presentation.ui.dailyRecord.adapter.SideEffectAdapter
import com.foregg.presentation.ui.dailyRecord.bottomSheet.DailyRecordEditDeleteBottomSheet
import com.foregg.presentation.util.ForeggNotification
import com.foregg.presentation.util.PendingExtraValue
import com.foregg.presentation.util.UserInfo
import com.kakao.sdk.user.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DailyRecordFragment : BaseFragment<FragmentDailyRecordBinding, DailyRecordPageState, DailyRecordViewModel>(
    FragmentDailyRecordBinding::inflate
) {
    private val dailyRecordAdapter : DailyRecordAdapter by lazy {
        DailyRecordAdapter(object : DailyRecordAdapter.DailyRecordDelegate {
            override fun onClickEmotion(request: PutEmotionVo) {
                viewModel.putEmotion(request)
            }

            override fun onClickDailyRecord(item: DailyRecordResponseItemVo) {
                showBottomSheet(item)
            }
        })
    }
    private val sideEffectAdapter = SideEffectAdapter()

    override val viewModel: DailyRecordViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    override fun initView() {
        binding.apply {
            vm = viewModel
            if (UserInfo.info.genderType == GenderType.FEMALE) { recordRecyclerView.adapter = dailyRecordAdapter }
            else {
                ForeggNotification.updateNoty(requireContext(), NotificationType.TODAY_RECORD_MALE, false)
                recordRecyclerView.adapter = dailyRecordAdapter
            }
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
                viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD)
                recordRecyclerView.adapter = dailyRecordAdapter
            }
            customTabBar.rightBtnClicked {
                viewModel.updateTabType(DailyRecordTabType.ADVERSE_EFFECT)
                recordRecyclerView.adapter = sideEffectAdapter
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
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateDailyRecord(id = -1L, index = 3, content = "")
        findNavController().navigate(action)
    }

    private fun goToEditDailyRecord(id: Long, index: Long, content: String) {
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateDailyRecord(id = id, index = index, content = content)
        findNavController().navigate(action)
    }

    private fun checkFcm(){
        if(requireActivity().intent.getStringExtra(PendingExtraValue.KEY) == PendingExtraValue.TODAY_RECORD_MALE) {
            binding.customTabBar.setRightBtnClickedBackground()
            viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD)
            binding.recordRecyclerView.adapter = dailyRecordAdapter
        }
    }

    private fun showBottomSheet(item: DailyRecordResponseItemVo) {
        val onClickBtnDelete = {
            dialog
                .setTitle(R.string.daily_record_delete_text)
                .setPositiveButton(R.string.word_yes) {
                    viewModel.deleteDailyRecord(item.id)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.word_no) {
                    dialog.dismiss()
                }
                .show()
        }
        val onClickBtnEdit = {
            goToEditDailyRecord(id = item.id, index = 3, content = item.content)
        }
        val bottomSheet = DailyRecordEditDeleteBottomSheet(onClickBtnDelete = onClickBtnDelete, onClickBtnEdit = onClickBtnEdit)
        bottomSheet.show(parentFragmentManager, "")

        bottomSheet.setOnDismissListener {
            val position = dailyRecordAdapter.getItemPosition(item)
            val viewHolder = binding.recordRecyclerView.findViewHolderForAdapterPosition(position) as? DailyRecordViewHolder
            viewHolder?.binding?.dailyRecordLayout?.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        }
    }
}