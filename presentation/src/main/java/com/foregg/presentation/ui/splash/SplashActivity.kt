package com.foregg.presentation.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseActivity
import com.foregg.presentation.databinding.ActivitySplashBinding
import com.foregg.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, PageState.Default, SplashViewModel>(
    ActivitySplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.checkLogin()
        }
    }

    override fun initState() {
        repeatOnStarted {
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToSign(){
        //TODO 로그인 화면으로 이동
    }
}