package com.foregg.presentation.ui.sign.splash

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.model.vo.UserVo
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase
) : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun checkLogin(){
        viewModelScope.launch {
            getMyInfoUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetMyInfo, { goToSign() }, false)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        val vo = UserVo(name = result.nickName, ssn = result.ssn, genderType = result.genderType)
        UserInfo.updateInfo(vo)
        goToMain()
    }

    private fun goToMain(){
        emitEventFlow(SplashEvent.GoToMainEvent)
    }

    private fun goToSign(){
        emitEventFlow(SplashEvent.GoToSignEvent)
    }
}