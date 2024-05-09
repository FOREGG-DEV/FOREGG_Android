package com.foregg.presentation.ui.main.profile.account

import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileAccountViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {

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
        //handleSuccessLogout()
    }

    fun unregister(){
        //handleSuccessUnregister()
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