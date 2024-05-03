package com.foregg.presentation.ui.main.account

import androidx.fragment.app.viewModels
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, PageState.Default, AccountViewModel>(
    FragmentAccountBinding::inflate) {

    override val viewModel: AccountViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

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

//    private fun sortEvent(event: OnboardingEvent){
//        when(event){
//            is OnboardingEvent.GoToSignUpEvent -> goToSignUp(event.token, event.shareCode)
//            OnboardingEvent.GoToMainEvent -> goToMain()
//            OnboardingEvent.MoveNextEvent -> moveToNext()
//            OnboardingEvent.KaKaoLoginEvent -> signInKakao()
//        }
//    }
}