package com.hugg.presentation.ui.sign.signUp.female

import com.hugg.domain.model.enums.SurgeryType
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class SignUpFemalePageState(
    val selectedSurgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<Int>,
    val emptyText : StateFlow<String>,
    val startTreatmentDay : StateFlow<String>,
    val shareCode : StateFlow<String>,
    val isExpand : StateFlow<Boolean>,
) : PageState