package com.foregg.presentation.ui.sign.signUp.chooseGender

import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseGenderViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {
    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")

    fun onClickFemale(){
        emitEventFlow(ChooseGenderEvent.OnClickFemaleEvent)
    }

    fun onClickMale(){
        emitEventFlow(ChooseGenderEvent.OnClickMaleEvent)
    }

    fun onClickBack(){
        emitEventFlow(ChooseGenderEvent.GoToBackEvent)
    }
}