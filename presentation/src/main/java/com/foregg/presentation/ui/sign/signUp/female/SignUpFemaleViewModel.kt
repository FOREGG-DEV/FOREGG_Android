package com.foregg.presentation.ui.sign.signUp.female

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFemaleViewModel @Inject constructor() : BaseViewModel<SignUpFemalePageState>() {

    private val surgeryTypeListStateFlow : MutableStateFlow<List<SurgeryType>> = MutableStateFlow(
        emptyList()
    )
    private val progressRoundStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val startTreatmentDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isExpandStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpFemalePageState = SignUpFemalePageState(
        surgeryTypeList = surgeryTypeListStateFlow.asStateFlow(),
        progressRound = progressRoundStateFlow,
        startTreatmentDay = startTreatmentDayStateFlow.asStateFlow(),
        shareCode = shareCodeStateFlow.asStateFlow(),
        isExpand = isExpandStateFlow.asStateFlow()
    )

    fun onClickBack(){
        emitEventFlow(SignUpFemaleEvent.GoToBackEvent)
    }

    fun getSurgeryType(){
        val list = listOf(SurgeryType.THINK_SURGERY, SurgeryType.EGG_FREEZING, SurgeryType.IN_VITRO_FERTILIZATION)
        viewModelScope.launch {
            surgeryTypeListStateFlow.update { list }
        }
    }

    fun onClickSpinner(){
        viewModelScope.launch {
            isExpandStateFlow.update { !isExpandStateFlow.value }
        }
    }
}