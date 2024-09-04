package com.hugg.presentation.ui

import com.hugg.domain.model.enums.BottomNavType
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MainActivityPageState(
    val pageType : StateFlow<BottomNavType>,
) : PageState