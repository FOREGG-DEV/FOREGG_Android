package com.foregg.presentation.ui.sign.signUp.male

import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpMaleViewModel @Inject constructor() : BaseViewModel<SignUpMalePageState>() {

    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: SignUpMalePageState = SignUpMalePageState(
        shareCodeStateFlow
    )

    fun onClickBack(){
        emitEventFlow(SignUpMaleEvent.GoToBackEvent)
    }

    fun onClickConfirm(){
        emitEventFlow(SignUpMaleEvent.GoToMainEvent)
    }
}