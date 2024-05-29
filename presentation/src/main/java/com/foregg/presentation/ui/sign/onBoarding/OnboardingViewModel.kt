package com.foregg.presentation.ui.sign.onBoarding

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.request.fcm.RenewalFcmRequestVo
import com.foregg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.model.vo.UserVo
import com.foregg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.foregg.domain.usecase.auth.PostLoginUseCase
import com.foregg.domain.usecase.auth.PostRenewalFcmUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.UserInfo
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val postRenewalFcmUseCase: PostRenewalFcmUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<OnboardingPageState>() {

    private val imageListStateFlow : MutableStateFlow<List<OnboardingTutorialVo>> = MutableStateFlow(emptyList())
    private val isLastPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: OnboardingPageState = OnboardingPageState(
        imageListStateFlow.asStateFlow(),
        isLastPageStateFlow.asStateFlow()
    )

    private lateinit var accessToken : String

    fun getTutorialImage(){
        viewModelScope.launch {
            imageListStateFlow.update { getTutorialList() }
        }
    }

    private fun getTutorialList() : List<OnboardingTutorialVo>{
        return listOf(
            OnboardingTutorialVo(
                title = resourceProvider.getString(R.string.onboarding_title_1),
                content = resourceProvider.getString(R.string.onboarding_content_1),
                img = R.drawable.onboarding_first
            ),
            OnboardingTutorialVo(
                title = resourceProvider.getString(R.string.onboarding_title_2),
                content = resourceProvider.getString(R.string.onboarding_content_2),
                img = R.drawable.onboarding_first
            ),
            OnboardingTutorialVo(
                title = resourceProvider.getString(R.string.onboarding_title_3),
                content = resourceProvider.getString(R.string.onboarding_content_3),
                img = R.drawable.onboarding_first
            ),
            OnboardingTutorialVo(
                title = resourceProvider.getString(R.string.onboarding_title_4),
                content = resourceProvider.getString(R.string.onboarding_content_4),
                img = R.drawable.onboarding_first
            )
        )
    }

    fun onClickNext(){
        emitEventFlow(OnboardingEvent.MoveNextEvent)
    }

    fun onClickSkip(){
        emitEventFlow(OnboardingEvent.SkipEvent)
    }

    fun onClickPrev(){
        emitEventFlow(OnboardingEvent.MovePrevEvent)
    }

    fun updateKaKaoLoginButton(value : Boolean){
        viewModelScope.launch {
            isLastPageStateFlow.update { value }
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
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            saveForeggAccessTokenAndRefreshTokenUseCase(request).collect{
                if(it) handleSaveTokenSuccess() else ForeggLog.D("저장 실패")
            }
        }
    }

    private fun handleSaveTokenSuccess(){
        updateFcmToken()
        getMyInfo()
    }

    private fun updateFcmToken(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = RenewalFcmRequestVo(token)
            viewModelScope.launch {
                postRenewalFcmUseCase(request).collect{
                    resultResponse(it, {ForeggLog.D("Fcm 토큰 갱신 성공")}, {ForeggLog.D("Fcm 토큰 갱신 실패")})
                }
            }
        }
    }

    private fun getMyInfo(){
        viewModelScope.launch {
            getMyInfoUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetMyInfo, {ForeggLog.D("오류")})
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        val vo = UserVo(name = result.nickName, ssn = result.ssn, genderType = result.genderType)
        UserInfo.updateInfo(vo)
        goToMain()
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