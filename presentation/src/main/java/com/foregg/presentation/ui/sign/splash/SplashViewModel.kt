package com.foregg.presentation.ui.sign.splash

import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun checkLogin(){
        //TODO 내 정보 가져오기 api를 통해 가입된 유저면 Main, 아니라면 sign으로
        emitEventFlow(SplashEvent.GoToOnboardingEvent)
    }

    private fun goToMain(){
        emitEventFlow(SplashEvent.GoToMainEvent)
    }

    private fun goToSign(){
        emitEventFlow(SplashEvent.GoToSignEvent)
    }
}