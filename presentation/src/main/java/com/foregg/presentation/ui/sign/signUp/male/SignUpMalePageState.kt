package com.foregg.presentation.ui.sign.signUp.male

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class SignUpMalePageState(
    var shareCode : MutableStateFlow<String>
) : PageState