package com.foregg.presentation.ui.main.account

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.AccountTabType
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
            override fun onClickDay(day: String) {

            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewAccountCard.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = accountCardAdapter
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
                    accountCardAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {

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

//    private fun sortEvent(event: OnboardingEvent){
//        when(event){
//            is OnboardingEvent.GoToSignUpEvent -> goToSignUp(event.token, event.shareCode)
//            OnboardingEvent.GoToMainEvent -> goToMain()
//            OnboardingEvent.MoveNextEvent -> moveToNext()
//            OnboardingEvent.KaKaoLoginEvent -> signInKakao()
//        }
//    }
}