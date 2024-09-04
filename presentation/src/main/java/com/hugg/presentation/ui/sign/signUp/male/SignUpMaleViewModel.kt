package com.hugg.presentation.ui.sign.signUp.male

import androidx.lifecycle.viewModelScope
import com.hugg.data.base.StatusCode
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.hugg.domain.model.response.SignResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.UserVo
import com.hugg.domain.usecase.auth.PostJoinMaleUseCase
import com.hugg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.hugg.domain.usecase.profile.GetMyInfoUseCase
import com.hugg.presentation.base.BaseViewModel
import com.hugg.presentation.util.UserInfo
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
                if(it) getMyInfo()
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
                resultResponse(it, ::handleSuccessGetMyInfo)
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