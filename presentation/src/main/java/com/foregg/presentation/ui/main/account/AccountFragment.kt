package com.foregg.presentation.ui.main.account

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.AccountTabType
import com.foregg.domain.model.vo.AccountCardVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentAccountBinding
import com.foregg.presentation.ui.main.account.adapter.AccountCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, AccountPageState, AccountViewModel>(
    FragmentAccountBinding::inflate) {

    override val viewModel: AccountViewModel by viewModels()

    private val accountCardAdapter : AccountCardAdapter by lazy {
        AccountCardAdapter(object : AccountCardAdapter.AccountCardDelegate{
            override fun onClickItem(vo: AccountCardVo) {
                goToCreateOrDetail(vo.id)
            }

            override fun onSelectItem(vo: AccountCardVo) {
                viewModel.updateSelectedCard(vo)
            }

            override fun changeMode(selectMode: Boolean) {
                val res = if(selectMode) R.drawable.ic_delete_main else R.drawable.ic_add_calendar
                binding.imgBtnAddAccount.setImageResource(res)
            }
        })
    }

    override fun initView() {
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
            customTabBar.leftTab.setOnClickListener {
                customTabBar.leftBtnClicked()
                viewModel.updateTabType(AccountTabType.ALL)
            }
            customTabBar.middleTab.setOnClickListener {
                customTabBar.middleBtnClicked()
                viewModel.updateTabType(AccountTabType.ROUND)
            }
            customTabBar.rightTab.setOnClickListener {
                customTabBar.rightBtnClicked()
                viewModel.updateTabType(AccountTabType.MONTH)
            }
        }
    }

    private fun goToCreateOrDetail(id : Long = -1){
        //TODO 상세 화면으로 이동
    }

    private fun hasSelectedItem(list : List<AccountCardVo>) : Boolean{
        if(list.isEmpty()) return false
        return list.any { it.isSelected }
    }

    private fun inspectEvent(event: AccountEvent){
        when(event){
            AccountEvent.OnClickAddOrDeleteBtn -> if(accountCardAdapter.getSelectMode()) viewModel.onClickDeleteBtn() else goToCreateOrDetail()
        }
    }
}