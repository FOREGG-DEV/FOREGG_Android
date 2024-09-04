package com.hugg.presentation.ui.main.profile.edit

import com.hugg.domain.model.enums.SurgeryType
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class EditMyInfoPageState(
    val selectedSurgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<Int>,
    val startTreatmentDay : StateFlow<String>,
    val shareCode : StateFlow<String>,
    val isExpand : StateFlow<Boolean>,
) : PageState