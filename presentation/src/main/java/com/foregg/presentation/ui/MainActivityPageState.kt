package com.foregg.presentation.ui

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MainActivityPageState(
    val isMainPage : StateFlow<Boolean>,
    val isKnotListPage : StateFlow<Boolean>,
    val isProfilePage : StateFlow<Boolean>,
    val isSettingPage : StateFlow<Boolean>,
    val isOtherPage : StateFlow<Boolean>
) : PageState