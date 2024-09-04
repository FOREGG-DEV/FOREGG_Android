package com.hugg.presentation.ui.dailyRecord.createDailyRecord

import androidx.fragment.app.viewModels
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentCreateDailyRecordBinding
import com.hugg.presentation.ui.common.CommonDialog
import com.hugg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateDailyRecordFragment : BaseFragment<FragmentCreateDailyRecordBinding, CreateDailyRecordPageState, CreateDailyRecordViewModel>(
    FragmentCreateDailyRecordBinding::inflate
) {

    override val viewModel: CreateDailyRecordViewModel by viewModels()

    private val arg : CreateDailyRecordFragmentArgs by navArgs()

    @Inject
    lateinit var dialog: CommonDialog

    override fun initView() {
        binding.apply {
            viewModel.setDailyRecordEditData(id = arg.id, content = arg.content, dailyConditionType = arg.dailyCondition)
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
            CreateDailyRecordEvent.InsufficientEmotionDataEvent -> ForeggToast.createToast(requireContext(), R.string.toast_error_choice_today_emotion,Toast.LENGTH_SHORT).show()
            CreateDailyRecordEvent.OnClickBtnClose -> findNavController().popBackStack()
            CreateDailyRecordEvent.InsufficientTextDataEvent -> ForeggToast.createToast(requireContext(), R.string.toast_error_choice_today_condition, Toast.LENGTH_SHORT).show()
            CreateDailyRecordEvent.ExistDailyRecordEvent -> showExistDailyRecordDialog()
            CreateDailyRecordEvent.SuccessEditDailyRecordEvent -> findNavController().popBackStack()
        }
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