package com.hugg.presentation.ui.main.profile

import com.hugg.domain.model.enums.SurgeryType
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class ProfilePageState(
    val nickName : StateFlow<String>,
    val surgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<String>,
    val startDay : StateFlow<String>,
    val spouse : StateFlow<String>,
) : PageState