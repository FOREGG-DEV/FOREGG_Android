package com.foregg.presentation.ui.sign.signUp.chooseGender

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow

data class ChooseGenderPageState(
    var ssn1 : MutableStateFlow<String>,
    var ssn2 : MutableStateFlow<String>,
    var ssn3 : MutableStateFlow<String>,
    var ssn4 : MutableStateFlow<String>,
    var ssn5 : MutableStateFlow<String>,
    var ssn6 : MutableStateFlow<String>,
    var ssn7 : MutableStateFlow<String>,
) : PageState