package com.foregg.presentation.ui.sign.onBoarding

import android.content.Intent
import androidx.fragment.app.viewModels
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentOnboardingBinding
import com.foregg.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, PageState.Default, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate) {

    override val viewModel: OnboardingViewModel by viewModels()
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
                    sortEvent(it as OnboardingEvent)
                }
            }
        }
    }

    private fun sortEvent(event: OnboardingEvent){
        when(event){
            OnboardingEvent.GoToSignUpEvent -> goToSignUp()
            OnboardingEvent.GoToMainEvent -> goToMain()
        }
    }

    private fun goToMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToSignUp(){
        //TODO 회원가입 화면으로 이동
    }
}