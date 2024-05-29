package com.foregg.presentation.ui.sign.onBoarding

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentOnboardingBinding
import com.foregg.presentation.ui.MainActivity
import com.foregg.presentation.ui.sign.onBoarding.adapter.OnboardingTutorialAdapter
import com.foregg.presentation.util.PendingExtraValue
import com.kakao.sdk.user.UserApiClient
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
                adapter = onboardingTutorialAdapter
                indicatorView.attachTo(viewPagerTutorial)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        imgBtnBack.visibility = if(position == 0) View.GONE else View.VISIBLE
                        if(position == adapter?.itemCount?.minus(1)) {
                            textSkip.visibility = View.GONE
                            viewModel.updateKaKaoLoginButton(true)
                        }
                        else {
                            textSkip.visibility = View.VISIBLE
                            viewModel.updateKaKaoLoginButton(false)
                        }
                    }
                })
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
            is OnboardingEvent.GoToSignUpEvent -> goToSignUp(event.token)
            OnboardingEvent.GoToMainEvent -> goToMain()
            OnboardingEvent.MoveNextEvent -> moveToNext()
            OnboardingEvent.KaKaoLoginEvent -> signInKakao()
            OnboardingEvent.MovePrevEvent -> moveToPrev()
            OnboardingEvent.SkipEvent -> moveToSkip()
        }
    }

    private fun moveToNext(){
        binding.apply {
            val nextItem = viewPagerTutorial.currentItem + 1
            if (nextItem < (viewPagerTutorial.adapter?.itemCount ?: 0)) {
                viewPagerTutorial.currentItem = nextItem
            }
        }
    }

    private fun moveToPrev(){
        binding.apply {
            val nextItem = viewPagerTutorial.currentItem - 1
            if (nextItem < (viewPagerTutorial.adapter?.itemCount ?: 0)) {
                viewPagerTutorial.currentItem = nextItem
            }
        }
    }

    private fun moveToSkip(){
        binding.apply {
            val nextItem = viewPagerTutorial.adapter?.itemCount?.minus(1)
            if (nextItem != null) {
                viewPagerTutorial.currentItem = nextItem
            }
        }
    }

    private fun signInKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            signInKakaoApp()
        } else {
            signInKakaoEmail()
        }
    }

    private fun signInKakaoApp() {
        UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
            token?.let {
                viewModel.login(token.accessToken)
            }
        }
    }

    private fun signInKakaoEmail() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
            token?.let {
                viewModel.login(token.accessToken)
            }
        }
    }

    private fun goToMain(){
        val pendingIntent = requireActivity().intent.getStringExtra(PendingExtraValue.KEY) ?: ""
        val targetIdIntent = requireActivity().intent.getLongExtra(PendingExtraValue.TARGET_ID_KEY, -1)
        val intent = Intent(requireActivity(), MainActivity::class.java).apply {
            if(pendingIntent.isNotEmpty()) putExtra(PendingExtraValue.KEY, pendingIntent)
            if(targetIdIntent != (-1).toLong()) putExtra(PendingExtraValue.TARGET_ID_KEY, targetIdIntent)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToSignUp(token : String){
        val action = OnboardingFragmentDirections.actionOnboardingToChooseGender(token)
        findNavController().navigate(action)
    }
}