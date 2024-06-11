package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import androidx.fragment.app.viewModels
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateDailyRecordBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateDailyRecordFragment : BaseFragment<FragmentCreateDailyRecordBinding, CreateDailyRecordPageState, CreateDailyRecordViewModel>(
    FragmentCreateDailyRecordBinding::inflate
) {

    override val viewModel: CreateDailyRecordViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    override fun initView() {
        binding.apply {
            val index = arguments?.getLong("index") ?: 0L
            val content = arguments?.getString("content") ?: ""
            val id = arguments?.getLong("id") ?: -1L
            viewModel.setDailyRecordEditData(id = id, content = content)
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.eventFlow.collect{
                sortEvent(it as CreateDailyRecordEvent)
            }
        }
    }

    private fun sortEvent(event: CreateDailyRecordEvent) {
        when(event) {
            CreateDailyRecordEvent.GoToCreateSideEffectEvent -> goToCreateSideEffect()
            CreateDailyRecordEvent.InsufficientEmotionDataEvent -> ForeggToast.createToast(requireContext(), "오늘의 감정을 선택해주세요.",Toast.LENGTH_SHORT).show()
            CreateDailyRecordEvent.OnClickBtnClose -> findNavController().popBackStack()
            CreateDailyRecordEvent.InsufficientTextDataEvent -> ForeggToast.createToast(requireContext(), "오늘의 컨디션을 입력해주세요.", Toast.LENGTH_SHORT).show()
            CreateDailyRecordEvent.ExistDailyRecordEvent -> showExistDailyRecordDialog()
            CreateDailyRecordEvent.SuccessEditDailyRecordEvent -> findNavController().popBackStack()
        }
    }

    private fun goToCreateSideEffect() {
        val action = CreateDailyRecordFragmentDirections.createDailyRecordToCreateSideEffect()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.createDailyRecordFragment, true)
            .build()
        findNavController().navigate(action.actionId, null, navOptions)
    }

    private fun showExistDailyRecordDialog() {
        dialog
            .setTitle(R.string.create_daily_record_error)
            .showOnlyPositiveBtn()
            .setPositiveButton(R.string.word_confirm){
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }
}