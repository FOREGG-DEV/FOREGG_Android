package com.foregg.presentation.ui.sign.signUp.female

import android.app.DatePickerDialog
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpFemaleBinding
import com.foregg.presentation.ui.MainActivity
import com.foregg.presentation.ui.sign.signUp.female.adapter.SurgeryTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class SignUpFemaleFragment : BaseFragment<FragmentSignUpFemaleBinding, SignUpFemalePageState, SignUpFemaleViewModel>(
    FragmentSignUpFemaleBinding::inflate
) {

    override val viewModel: SignUpFemaleViewModel by viewModels()

    private val signUpFemaleFragmentArgs : SignUpFemaleFragmentArgs by navArgs()

    private val surgeryTypeAdapter : SurgeryTypeAdapter by lazy {
        SurgeryTypeAdapter(object : SurgeryTypeAdapter.SurgeryTypeDelegate{
            override fun onClickType(type: SurgeryType) {
                setSurgeryType(type)
                viewModel.updateSelectedSurgeryType(type)
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
                adapter = surgeryTypeAdapter
            }
            viewModel.getSurgeryType(signUpFemaleFragmentArgs.accessToken)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.surgeryTypeList.collect{
                    surgeryTypeAdapter.submitList(it)
                }
            }

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

    private fun setSurgeryType(type : SurgeryType){
        binding.apply {
            textSurgeryType.text = type.type
        }
    }
}