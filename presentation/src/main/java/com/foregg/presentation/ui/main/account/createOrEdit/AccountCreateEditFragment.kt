package com.foregg.presentation.ui.main.account.createOrEdit

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.foregg.domain.model.enums.AccountType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AccountCreateEditFragment : BaseFragment<FragmentCreateEditAccountBinding, AccountCreateEditPageState, AccountCreateEditViewModel>(
    FragmentCreateEditAccountBinding::inflate
) {

    private val accountCreateEditFragmentArgs : AccountCreateEditFragmentArgs by navArgs()

    override val viewModel: AccountCreateEditViewModel by viewModels()

    private val calendar = Calendar.getInstance()
    private val datePickerDialog : DatePickerDialog by lazy { DatePickerDialog(requireContext(),
        R.style.DatePickerStyle,
        null,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ) }

    override fun initView() {
        binding.apply {
            vm = viewModel
            bindTab()
            bindEditText()
            viewModel.setViewType(accountCreateEditFragmentArgs)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as AccountCreateEditEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: AccountCreateEditEvent){
        when(event){
            AccountCreateEditEvent.ShowDatePickerDialog -> showDatePickerDialog()
            AccountCreateEditEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun showDatePickerDialog(){
        datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
            val formattedMonth = String.format("%02d", month + 1)
            val formattedDay = String.format("%02d", day)
            viewModel.setDate( "$year-$formattedMonth-$formattedDay")
        }
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
    }

    private fun bindTab(){
        binding.apply {
            customTabBar.leftTab.setOnClickListener {
                customTabBar.leftBtnClicked()
                viewModel.setTabType(AccountType.PERSONAL_EXPENSE)
            }

            customTabBar.rightTab.setOnClickListener {
                customTabBar.rightBtnClicked()
                viewModel.setTabType(AccountType.SUBSIDY)
            }
        }
    }

    private fun bindEditText(){
        binding.editTextMoney.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.editTextMoney.setSelection(binding.editTextMoney.text.length)  //커서를 오른쪽 끝으로 보냄
            }
        })
    }
}