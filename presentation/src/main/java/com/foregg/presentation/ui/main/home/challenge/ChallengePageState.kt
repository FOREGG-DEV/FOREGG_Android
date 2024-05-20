package com.foregg.presentation.ui.main.home.challenge

import com.foregg.domain.model.enums.ChallengeTapType
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class ChallengePageState (
    val challengeTapType: StateFlow<ChallengeTapType>,
    val allItemCount: StateFlow<Int>,
    val currentItemCount: StateFlow<Int>,
    val challengeList: StateFlow<List<ChallengeCardVo>>,
    val challengeMonthWeek: StateFlow<String>,
    val myChallengeList: StateFlow<List<MyChallengeListItemVo>>,
    val allMyChallengeItemCount: StateFlow<Int>,
    val currentMyChallengeItemCount: StateFlow<Int>,
    val participateBtnBackground: StateFlow<Int>,
    val participateBtnText: StateFlow<String>,
    val participateBtnTextColor: StateFlow<Int>,
    val weekOfMonth: StateFlow<String>,
): PageState