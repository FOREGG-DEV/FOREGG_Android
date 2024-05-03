package com.foregg.presentation.ui.sign.signUp.chooseGender

import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChooseGenderViewModel @Inject constructor() : BaseViewModel<ChooseGenderPageState>() {

    private val ssn1StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn2StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn3StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn4StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn5StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn6StateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val ssn7StateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: ChooseGenderPageState = ChooseGenderPageState(
        ssn1 = ssn1StateFlow,
        ssn2 = ssn2StateFlow,
        ssn3 = ssn3StateFlow,
        ssn4 = ssn4StateFlow,
        ssn5 = ssn5StateFlow,
        ssn6 = ssn6StateFlow,
        ssn7 = ssn7StateFlow,
    )

    private fun goToFemale(){
        emitEventFlow(ChooseGenderEvent.OnClickFemaleEvent(getSsn()))
    }

    private fun goToMale(){
        emitEventFlow(ChooseGenderEvent.OnClickMaleEvent(getSsn()))
    }

    fun onClickBack(){
        emitEventFlow(ChooseGenderEvent.GoToBackEvent)
    }

    fun onClickNext(){
        if(isEmpty()) return
        if(ssn7StateFlow.value.toInt() % 2 == 0) goToFemale() else goToMale()
    }

    private fun getSsn() : String{
        return "${ssn1StateFlow.value}${ssn2StateFlow.value}${ssn3StateFlow.value}${ssn4StateFlow.value}" +
                "${ssn5StateFlow.value}${ssn6StateFlow.value}-${ssn7StateFlow.value}"
    }

    private fun isEmpty() : Boolean{
        return ssn1StateFlow.value.isEmpty() && ssn2StateFlow.value.isEmpty() && ssn3StateFlow.value.isEmpty()
                && ssn4StateFlow.value.isEmpty() && ssn5StateFlow.value.isEmpty() && ssn6StateFlow.value.isEmpty()
                && ssn7StateFlow.value.isEmpty()
    }
}