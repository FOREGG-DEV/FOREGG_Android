package com.foregg.presentation.ui.sign.splash

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSplashBinding
import com.foregg.presentation.ui.MainActivity
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, PageState.Default, SplashViewModel>(
    FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
            val keyHash = Utility.getKeyHash(requireContext())
            Log.d("KAKAO HASH", keyHash)
            viewModel.checkLogin()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as SplashEvent)
                }
            }
        }
    }

    private fun sortEvent(event: SplashEvent){
        when(event){
            SplashEvent.GoToSignEvent -> goToSign()
            SplashEvent.GoToMainEvent -> goToMain()
            SplashEvent.GoToOnboardingEvent -> goToOnboarding()
        }
    }

    private fun goToMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToSign(){
        //TODO 로그인 화면으로 이동
    }

    private fun goToOnboarding(){
        val action = SplashFragmentDirections.actionSplashToOnboarding()
        findNavController().navigate(action)
    }
}