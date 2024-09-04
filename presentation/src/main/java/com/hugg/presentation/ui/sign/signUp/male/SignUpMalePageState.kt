package com.hugg.presentation.ui.sign.signUp.male

import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class SignUpMalePageState(
    var shareCode : MutableStateFlow<String>
) : PageState