package com.foregg.presentation.ui.main.injection

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class InjectionPageState(
    val date : StateFlow<String>,
    val time : StateFlow<String>,
    val injection : StateFlow<String>,
) : PageState