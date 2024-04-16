package com.foregg.presentation.ui.sign.signUp.female

import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignUpFemalePageState(
    val surgeryTypeList : StateFlow<List<SurgeryType>>,
    val selectedSurgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<Int>,
    val startTreatmentDay : StateFlow<String>,
    val shareCode : StateFlow<String>,
    val isExpand : StateFlow<Boolean>,
) : PageState
