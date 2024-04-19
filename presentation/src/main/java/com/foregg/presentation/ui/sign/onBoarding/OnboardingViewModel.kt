package com.foregg.presentation.ui.sign.onBoarding

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.request.SaveForeggJwtRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.usecase.auth.PostLoginUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase
) : BaseViewModel<OnboardingPageState>() {

    private val imageListStateFlow : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val isLastPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: OnboardingPageState = OnboardingPageState(
        imageListStateFlow.asStateFlow(),
        isLastPageStateFlow.asStateFlow()
    )

    private lateinit var accessToken : String

    fun getTutorialImage(){
        //TODO 설명 및 이미지 받아와야 함
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
        emitEventFlow(OnboardingEvent.KaKaoLoginEvent)
    }

    fun login(token : String){
        accessToken = token
        viewModelScope.launch {
            postLoginUseCase(token).collect{
                resultResponse(it, ::handleLoginSuccess, ::handleLoginError)
            }
        }
    }

    private fun handleLoginSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = "")
        viewModelScope.launch {
            saveForeggAccessTokenAndRefreshTokenUseCase(request).collect{
                if(it) goToMain() else ForeggLog.D("저장 실패")
            }
        }
    }

    private fun handleLoginError(error : String){
        when(error){
            StatusCode.AUTH.USER_NEED_JOIN -> goToSignUp()
            else -> ForeggLog.D("알 수 없는 오류")
        }
    }

    private fun goToMain(){
        emitEventFlow(OnboardingEvent.GoToMainEvent)
    }

    private fun goToSignUp(){
        emitEventFlow(OnboardingEvent.GoToSignUpEvent(accessToken))
    }
}