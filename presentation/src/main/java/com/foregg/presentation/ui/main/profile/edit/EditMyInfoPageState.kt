package com.foregg.presentation.ui.main.profile.edit

import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class EditMyInfoPageState(
    val selectedSurgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<Int>,
    val startTreatmentDay : StateFlow<String>,
    val shareCode : StateFlow<String>,
    val isExpand : StateFlow<Boolean>,
) : PageState