package com.foregg.presentation.ui.sign.onBoarding

import androidx.lifecycle.viewModelScope
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BaseViewModel<OnboardingPageState>() {

    private val imageListStateFlow : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val isLastPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: OnboardingPageState = OnboardingPageState(
        imageListStateFlow.asStateFlow(),
        isLastPageStateFlow.asStateFlow()
    )

    fun getTutorialImage(){
        val list = listOf("여기에 그림에 들어가는 기능에 대한 설명 멘트 두 줄 정도 들어가면 좋을 것 같습니다.", "2번", "3번", "4번")
        viewModelScope.launch {
            imageListStateFlow.update { list }
        }
    }

    fun onClickNext(){
        emitEventFlow(OnboardingEvent.MoveNextEvent)
    }

    fun updateKaKaoLoginButton(){
        viewModelScope.launch {
            isLastPageStateFlow.update { true }
        }
    }

    fun onClickKaKaoLogin(){
        //TODO 카카오 로그인
    }

    private fun goToMain(){
        emitEventFlow(OnboardingEvent.GoToMainEvent)
    }

    private fun goToSignUp(){
        emitEventFlow(OnboardingEvent.GoToSignUpEvent)
    }
}