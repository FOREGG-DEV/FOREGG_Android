package com.foregg.presentation.ui.main.profile

import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfilePageState(
    val nickName : StateFlow<String>,
    val surgeryType: StateFlow<SurgeryType>,
    val progressRound : StateFlow<String>,
    val startDay : StateFlow<String>,
    val spouse : StateFlow<String>,
) : PageState