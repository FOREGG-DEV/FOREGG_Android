package com.foregg.presentation.ui.sign.signUp.female

import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFemaleViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onClickBack(){
        emitEventFlow(SignUpFemaleEvent.GoToBackEvent)
    }
}