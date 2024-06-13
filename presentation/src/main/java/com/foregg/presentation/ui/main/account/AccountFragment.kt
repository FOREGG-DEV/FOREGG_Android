package com.foregg.presentation.ui.main.account

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.AccountTabType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.NotificationType
import com.foregg.domain.model.vo.account.AccountCardVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentAccountBinding
import com.foregg.presentation.ui.main.account.adapter.AccountCardAdapter
import com.foregg.presentation.ui.main.account.bottomSheet.AccountDatePickBottomSheet
import com.foregg.presentation.util.ForeggNotification
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, AccountPageState, AccountViewModel>(
    FragmentAccountBinding::inflate) {

    override val viewModel: AccountViewModel by viewModels()

    private val accountCardAdapter : AccountCardAdapter by lazy {
        AccountCardAdapter(object : AccountCardAdapter.AccountCardDelegate{
            override fun onClickItem(vo: AccountCardVo) {
                goToCreateOrDetail(vo.id, CalendarType.EDIT)
            }

            override fun onSelectItem(vo: AccountCardVo) {
                viewModel.updateSelectedCard(vo)
            }

            override fun changeMode(selectMode: Boolean) {
                val res = if(selectMode) R.drawable.ic_delete_main else R.drawable.ic_btn_add_filled_main
                binding.imgBtnAddAccount.setImageResource(res)
            }
        })
    }

    override fun initView() {
        ForeggNotification.updateNoty(requireContext(), NotificationType.LEDGER, false)
        binding.apply {
            vm = viewModel

            recyclerViewAccountCard.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = accountCardAdapter
                itemAnimator = null
                isNestedScrollingEnabled = false
            }

            bindTab()
            viewModel.setView()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.accountList.collect{
                    if(accountCardAdapter.getSelectMode() && !hasSelectedItem(it)) accountCardAdapter.changeMode()
                    accountCardAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as AccountEvent)
                }
            }
        }
    }

    private fun bindTab(){
        binding.apply {
            customTabBar.leftBtnClicked {
                viewModel.updateTabType(AccountTabType.ALL)
            }
            customTabBar.middleBtnClicked {
                viewModel.updateTabType(AccountTabType.ROUND)
            }
            customTabBar.rightBtnClicked {
                viewModel.updateTabType(AccountTabType.MONTH)
            }
        }
    }

    private fun goToCreateOrDetail(id : Long = -1, type : CalendarType){
        val action = AccountFragmentDirections.actionAccountToCreateEdit(id, type)
        findNavController().navigate(action)
    }

    private fun hasSelectedItem(list : List<AccountCardVo>) : Boolean{
        if(list.isEmpty()) return false
        return list.any { it.isSelected }
    }

    private fun inspectEvent(event: AccountEvent){
        when(event){
            AccountEvent.OnClickAddOrDeleteBtn -> if(accountCardAdapter.getSelectMode()) viewModel.onClickDeleteBtn() else goToCreateOrDetail(type = CalendarType.CREATE)
            is AccountEvent.ShowBottomSheetEvent -> showBottomSheet(event.startDay, event.endDay)
            AccountEvent.ErrorNotExist -> ForeggToast.createToast(requireContext(), R.string.toast_error_no_exist_ledger, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBottomSheet(startDay : String, endDay : String){
        AccountDatePickBottomSheet.newInstance(startDay, endDay) { start, end ->
            viewModel.initDay(start, end)
            viewModel.getAccountByCondition()
        }.show(parentFragmentManager, "")
    }
}