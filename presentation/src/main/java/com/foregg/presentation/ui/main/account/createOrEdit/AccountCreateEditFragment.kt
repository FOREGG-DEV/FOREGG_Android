package com.foregg.presentation.ui.main.account.createOrEdit

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.foregg.domain.model.enums.AccountType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateEditAccountBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AccountCreateEditFragment : BaseFragment<FragmentCreateEditAccountBinding, AccountCreateEditPageState, AccountCreateEditViewModel>(
    FragmentCreateEditAccountBinding::inflate
) {
    @Inject
    lateinit var commonDialog : CommonDialog

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
                viewModel.uiState.money.collect{
                    viewModel.updateChangedOrigin()
                }
            }
            launch {
                viewModel.uiState.tabType.collect{
                    if(it == AccountType.PERSONAL_EXPENSE) binding.customTabBar.setLeftBtnClickedBackground()
                    else binding.customTabBar.setRightBtnClickedBackground()
                }
            }
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
            AccountCreateEditEvent.ErrorNotExist -> showErrorDialog()
            AccountCreateEditEvent.ErrorBlankContent -> ForeggToast.createToast(requireContext(), R.string.toast_error_blank_content_schedule, Toast.LENGTH_SHORT).show()
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
            customTabBar.leftBtnClicked { viewModel.setTabType(AccountType.PERSONAL_EXPENSE) }
            customTabBar.rightBtnClicked { viewModel.setTabType(AccountType.SUBSIDY) }
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

    private fun showErrorDialog(){
        commonDialog
            .setTitle(R.string.toast_error_no_exist_ledger)
            .showOnlyPositiveBtn()
            .setPositiveButton(R.string.word_confirm){
                commonDialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }
}