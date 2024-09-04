package com.hugg.presentation.ui.main.injection

import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class InjectionPageState(
    val date : StateFlow<String>,
    val time : StateFlow<String>,
    val injection : StateFlow<String>,
    val image : StateFlow<String>,
    val explain : StateFlow<String>
) : PageState