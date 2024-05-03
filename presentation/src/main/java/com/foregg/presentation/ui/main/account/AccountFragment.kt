package com.foregg.presentation.ui.main.account

import androidx.fragment.app.viewModels
import com.foregg.domain.model.enums.AccountTabType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, AccountPageState, AccountViewModel>(
    FragmentAccountBinding::inflate) {

    override val viewModel: AccountViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            bindTab()
            viewModel.setView()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
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