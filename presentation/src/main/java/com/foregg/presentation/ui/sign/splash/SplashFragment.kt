package com.foregg.presentation.ui.sign.splash

import android.content.Intent
import androidx.fragment.app.viewModels
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSplashBinding
import com.foregg.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, PageState.Default, SplashViewModel>(
    FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel

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
}