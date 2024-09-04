package com.hugg.presentation.ui.sign.signUp.female

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugg.domain.model.enums.SurgeryType
import com.hugg.presentation.R
import com.hugg.presentation.base.BaseFragment
import com.hugg.presentation.databinding.FragmentSignUpFemaleBinding
import com.hugg.presentation.ui.MainActivity
import com.hugg.presentation.ui.common.spinner.CommonSpinnerAdapter
import com.hugg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class SignUpFemaleFragment : BaseFragment<FragmentSignUpFemaleBinding, SignUpFemalePageState, SignUpFemaleViewModel>(
    FragmentSignUpFemaleBinding::inflate
) {

    override val viewModel: SignUpFemaleViewModel by viewModels()

    private val signUpFemaleFragmentArgs : SignUpFemaleFragmentArgs by navArgs()

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
            viewModel.getSurgeryType(signUpFemaleFragmentArgs)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as SignUpFemaleEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SignUpFemaleEvent){
        when(event){
            SignUpFemaleEvent.GoToBackEvent -> findNavController().popBackStack()
            SignUpFemaleEvent.ShowDatePickerDialogEvent -> showDatePickerDialog()
            SignUpFemaleEvent.GoToMainEvent -> goToMain()
            is SignUpFemaleEvent.OnClickCopyCodeEvent -> copyClipBoard(event.code)
            SignUpFemaleEvent.ErrorEmptyDate -> ForeggToast.createToast(requireContext(), R.string.toast_error_empty_start_date, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
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
        ForeggToast.createToast(requireContext(), R.string.toast_clip_share_code, Toast.LENGTH_SHORT).show()
    }
}