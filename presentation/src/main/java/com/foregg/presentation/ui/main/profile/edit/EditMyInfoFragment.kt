package com.foregg.presentation.ui.main.profile.edit

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentEditMyInfoBinding
import com.foregg.presentation.ui.common.spinner.CommonSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class EditMyInfoFragment : BaseFragment<FragmentEditMyInfoBinding, EditMyInfoPageState, EditMyInfoViewModel>(
    FragmentEditMyInfoBinding::inflate
) {

    override val viewModel: EditMyInfoViewModel by viewModels()

    private val spinnerAdapter : CommonSpinnerAdapter by lazy {
        CommonSpinnerAdapter(object : CommonSpinnerAdapter.CommonSpinnerDelegate{
            override fun onClickType(type: String) {
                viewModel.updateSelectedSurgeryType(SurgeryType.valuesOf(type))
            }
        })
    }

    private val calendar = Calendar.getInstance()
    private val listener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        val formattedMonth = String.format("%02d", month + 1)
        val formattedDay = String.format("%02d", day)
        viewModel.updateStartTreatmentDay("$year-$formattedMonth-$formattedDay")
    }

    private val datePickerDialog : DatePickerDialog by lazy { DatePickerDialog(requireContext(),
        R.style.DatePickerStyle,
        listener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ) }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerSugeryType.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = spinnerAdapter
            }
            spinnerAdapter.submitList(resources.getStringArray(R.array.surgery_list).toList())
            viewModel.getMyInfo()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as EditMyInfoEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: EditMyInfoEvent){
        when(event){
            EditMyInfoEvent.GoToBackEvent -> findNavController().popBackStack()
            EditMyInfoEvent.ShowDatePickerDialogEvent -> showDatePickerDialog()
            is EditMyInfoEvent.OnClickCopyCodeEvent -> copyClipBoard(event.code)
        }
    }

    private fun showDatePickerDialog(){
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
    }

    private fun copyClipBoard(code : String){
        val clipboard: ClipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", code)
        clipboard.setPrimaryClip(clip)
    }
}