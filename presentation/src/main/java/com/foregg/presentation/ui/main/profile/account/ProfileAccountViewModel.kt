package com.foregg.presentation.ui.main.profile.account

import androidx.lifecycle.viewModelScope
import com.foregg.domain.usecase.auth.PostLogoutUseCase
import com.foregg.domain.usecase.auth.PostUnregisterUseCase
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAccountViewModel @Inject constructor(
    private val postLogoutUseCase: PostLogoutUseCase,
    private val postUnregisterUseCase: PostUnregisterUseCase
) : BaseViewModel<PageState.Default>() {

    fun onClickBack(){
        emitEventFlow(ProfileAccountEvent.GoToBackEvent)
    }

    fun onClickLogout(){
        emitEventFlow(ProfileAccountEvent.OnClickLogoutEvent)
    }

    fun onClickUnregister(){
        emitEventFlow(ProfileAccountEvent.OnClickUnregisterEvent)
    }

    fun logout(){
        viewModelScope.launch {
            postLogoutUseCase(Unit).collect{
                resultResponse(it, { handleSuccessLogout() })
            }
        }
    }

    fun unregister(){
        viewModelScope.launch {
            postUnregisterUseCase(Unit).collect{
                resultResponse(it, { handleSuccessUnregister() })
            }
        }
    }

    private fun handleSuccessLogout(){
        emitEventFlow(ProfileAccountEvent.CompleteLogoutEvent)
    }

    private fun handleSuccessUnregister(){
        emitEventFlow(ProfileAccountEvent.CompleteLogoutEvent)
    }

    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}