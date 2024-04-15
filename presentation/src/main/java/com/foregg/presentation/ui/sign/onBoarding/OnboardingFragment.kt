package com.foregg.presentation.ui.sign.onBoarding

import android.content.Intent
import androidx.fragment.app.viewModels
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentOnboardingBinding
import com.foregg.presentation.ui.MainActivity
import com.foregg.presentation.ui.sign.onBoarding.adapter.OnboardingTutorialAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingPageState, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate) {

    override val viewModel: OnboardingViewModel by viewModels()

    private val onboardingTutorialAdapter = OnboardingTutorialAdapter()
    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPagerTutorial.apply {
                isUserInputEnabled = false
                adapter = onboardingTutorialAdapter
                indicatorView.attachTo(viewPagerTutorial)
            }

            viewModel.getTutorialImage()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.imageList.collect {
                    onboardingTutorialAdapter.submitList(it)
                }
            }
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
            OnboardingEvent.MoveNextEvent -> moveToNext()
        }
    }

    private fun moveToNext(){
        binding.apply {
            val nextItem = viewPagerTutorial.currentItem + 1
            if (nextItem < (viewPagerTutorial.adapter?.itemCount ?: 0)) {
                viewPagerTutorial.currentItem = nextItem
            }
            if(nextItem == (viewPagerTutorial.adapter?.itemCount?.minus(1))){
                viewModel.updateKaKaoLoginButton()
            }
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