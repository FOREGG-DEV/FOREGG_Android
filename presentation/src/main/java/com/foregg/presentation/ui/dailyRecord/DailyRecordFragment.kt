package com.foregg.presentation.ui.dailyRecord

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentDailyRecordBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordAdapter
import com.foregg.presentation.ui.dailyRecord.adapter.DailyRecordViewHolder
import com.foregg.presentation.ui.dailyRecord.adapter.SideEffectAdapter
import com.foregg.presentation.ui.dailyRecord.adapter.SideEffectViewHolder
import com.foregg.presentation.ui.dailyRecord.bottomSheet.DailyRecordEditDeleteBottomSheet
import com.foregg.presentation.util.PendingExtraValue
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
                showDailyBottomSheet(item)
            }
        })
    }
    private val sideEffectAdapter : SideEffectAdapter by lazy {
        SideEffectAdapter(object : SideEffectAdapter.SideEffectDelegate {
            override fun onLongClickSideEffect(item: SideEffectListItemVo) {
                showSideEffectBottomSheet(item)
            }
        })
    }

    override val viewModel: DailyRecordViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    override fun initView() {
        binding.apply {
            vm = viewModel
            recordRecyclerView.apply {
                adapter = dailyRecordAdapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
            }
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
                    dailyRecordAdapter.submitList(it)
                    scrollToTop()
                }
            }

            launch {
                viewModel.uiState.sideEffectList.collect {
                    sideEffectAdapter.submitList(it)
                    scrollToTop()
                }
            }

            launch {
                viewModel.uiState.dailyRecordTabType.collect{
                    initTab(it)
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

    private fun initTab(type : DailyRecordTabType){
        when(type){
            DailyRecordTabType.ADVERSE_EFFECT -> {
                binding.customTabBar.setRightBtnClickedBackground()
                binding.recordRecyclerView.adapter = sideEffectAdapter
            }
            DailyRecordTabType.DAILY_RECORD -> {
                binding.customTabBar.setLeftBtnClickedBackground()
                binding.recordRecyclerView.adapter = dailyRecordAdapter
            }
        }
    }

    private fun sortEvent(event: DailyRecordEvent) {
        when(event) {
            DailyRecordEvent.GoToCreateDailyRecordEvent -> goToCreateOrEditDailyRecord(id = -1L)
            DailyRecordEvent.OnClickBtnClose -> findNavController().popBackStack()
            DailyRecordEvent.GoToCreateSideEffectEvent -> goToCreateOrEditSideEffect()
        }
    }

    private fun goToCreateOrEditDailyRecord(id: Long = -1L, content: String = "", dailyCondition : DailyConditionType = DailyConditionType.DEFAULT) {
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateDailyRecord(id = id, content = content, dailyCondition = dailyCondition)
        findNavController().navigate(action)
    }

    private fun goToCreateOrEditSideEffect(id: Long = -1L, content: String = "") {
        val action = DailyRecordFragmentDirections.actionDailyRecordToCreateSideEffect(id = id, content = content)
        findNavController().navigate(action)
    }

    private fun checkFcm(){
        if(requireActivity().intent.getStringExtra(PendingExtraValue.KEY) == PendingExtraValue.TODAY_RECORD_MALE) {
            binding.customTabBar.setRightBtnClickedBackground()
            viewModel.updateTabType(DailyRecordTabType.DAILY_RECORD)
            binding.recordRecyclerView.adapter = dailyRecordAdapter
        }
    }

    private fun showDailyBottomSheet(item: DailyRecordResponseItemVo, ) {
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
            goToCreateOrEditDailyRecord(id = item.id, content = item.content, dailyCondition = item.dailyConditionType)
        }
        val bottomSheet = DailyRecordEditDeleteBottomSheet(onClickBtnDelete = onClickBtnDelete, onClickBtnEdit = onClickBtnEdit)
        bottomSheet.show(parentFragmentManager, "")

        bottomSheet.setOnDismissListener {
            val position = dailyRecordAdapter.getItemPosition(item)
            val viewHolder = binding.recordRecyclerView.findViewHolderForAdapterPosition(position) as? DailyRecordViewHolder
            viewHolder?.binding?.dailyRecordLayout?.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        }
    }

    private fun showSideEffectBottomSheet(item: SideEffectListItemVo, ) {
        val onClickBtnDelete = {
            dialog
                .setTitle(R.string.side_effect_delete_text)
                .setPositiveButton(R.string.word_yes) {
                    viewModel.deleteSideEffectRecord(item.id)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.word_no) {
                    dialog.dismiss()
                }
                .show()
        }
        val onClickBtnEdit = {
            goToCreateOrEditSideEffect(id = item.id, content = item.content)
        }
        val bottomSheet = DailyRecordEditDeleteBottomSheet(onClickBtnDelete = onClickBtnDelete, onClickBtnEdit = onClickBtnEdit)
        bottomSheet.show(parentFragmentManager, "")

        bottomSheet.setOnDismissListener {
            val position = sideEffectAdapter.getItemPosition(item)
            val viewHolder = binding.recordRecyclerView.findViewHolderForAdapterPosition(position) as? SideEffectViewHolder
            viewHolder?.binding?.layoutSideEffect?.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        }
    }

    private fun scrollToTop(){
        binding.apply {
            recordRecyclerView.post {
                recordRecyclerView.scrollToPosition(0)
            }
        }
    }
}