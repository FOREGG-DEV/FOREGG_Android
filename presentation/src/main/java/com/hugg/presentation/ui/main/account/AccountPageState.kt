package com.hugg.presentation.ui.main.account

import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class AccountPageState(
    val tabType : StateFlow<AccountTabType>,
    val selectText : StateFlow<String>,
    val startDay : StateFlow<String>,
    val endDay : StateFlow<String>,
    val startAndEnd : StateFlow<String>,
    val allExpend : StateFlow<String>,
    val subsidy : StateFlow<String>,
    val personal : StateFlow<String>,
    val accountList : StateFlow<List<AccountCardVo>>
) : PageState
