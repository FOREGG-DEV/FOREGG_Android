package com.foregg.presentation.ui

import com.foregg.domain.model.enums.BottomNavType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MainActivityPageState(
    val pageType : StateFlow<BottomNavType>,
) : PageState