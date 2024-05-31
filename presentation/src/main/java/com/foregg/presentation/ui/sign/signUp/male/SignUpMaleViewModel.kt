package com.foregg.presentation.ui.sign.signUp.male

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.foregg.domain.model.request.sign.SignUpMaleRequestVo
import com.foregg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.model.vo.UserVo
import com.foregg.domain.usecase.auth.PostJoinMaleUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.UserInfo
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpMaleViewModel @Inject constructor(
    private val postJoinMaleUseCase: PostJoinMaleUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : BaseViewModel<SignUpMalePageState>() {

    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: SignUpMalePageState = SignUpMalePageState(
        shareCodeStateFlow
    )

    private lateinit var accessToken : String
    private lateinit var ssn : String

    fun setMaleInfo(args: SignUpMaleFragmentArgs){
        this.accessToken = args.accessToken
        this.ssn = args.ssn
    }

    fun onClickBack(){
        emitEventFlow(SignUpMaleEvent.GoToBackEvent)
    }

    fun onClickConfirm(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = getRequest(token)
            viewModelScope.launch {
                postJoinMaleUseCase(request).collect{
                    resultResponse(it, ::handleJoinSuccess, ::handleJoinError)
                }
            }
        }
    }

    private fun handleJoinSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            saveForeggAccessTokenAndRefreshTokenUseCase(request).collect{
                if(it) getMyInfo() else ForeggLog.D("저장 실패")
            }
        }
    }

    private fun handleJoinError(error : String){
        when(error){
            StatusCode.AUTH.NOT_CORRECT_SHARE_CODE -> emitEventFlow(SignUpMaleEvent.ErrorShareCode)
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
        val vo = UserVo(name = result.nickName, ssn = result.ssn, genderType = result.genderType, spouse = result.spouse)
        UserInfo.updateInfo(vo)
        goToMain()
    }

    private fun goToMain(){
        emitEventFlow(SignUpMaleEvent.GoToMainEvent)
    }
    private fun getRequest(fcmToken : String) : SignUpWithTokenMaleRequestVo {
        return SignUpWithTokenMaleRequestVo(
            accessToken = accessToken,
            signUpMaleRequestVo = SignUpMaleRequestVo(spouseCode = shareCodeStateFlow.value, ssn = ssn, fcmToken = fcmToken)
        )
    }
}