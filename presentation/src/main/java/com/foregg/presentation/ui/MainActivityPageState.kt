package com.foregg.presentation.ui

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MainActivityPageState(
    val isCalendarPage : StateFlow<Boolean>,
    val isAccountPage : StateFlow<Boolean>,
    val isMainPage : StateFlow<Boolean>,
    val isInfoPage : StateFlow<Boolean>,
    val isProfilePage : StateFlow<Boolean>,
    val isOtherPage : StateFlow<Boolean>
) : PageState