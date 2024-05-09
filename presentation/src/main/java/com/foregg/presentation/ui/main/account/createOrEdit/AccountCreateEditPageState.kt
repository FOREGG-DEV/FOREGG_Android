package com.foregg.presentation.ui.main.account.createOrEdit

import com.foregg.domain.model.enums.AccountType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AccountCreateEditPageState(
    val viewType : StateFlow<CalendarType>,
    val tabType : StateFlow<AccountType>,
    val selectDate : StateFlow<String>,
    var content : MutableStateFlow<String>,
    var money : MutableStateFlow<String>,
    val round : StateFlow<Int>,
    var memo : MutableStateFlow<String>,
    val isChanged : StateFlow<Boolean>
) : PageState