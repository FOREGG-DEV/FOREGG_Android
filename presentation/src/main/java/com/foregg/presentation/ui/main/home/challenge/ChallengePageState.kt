package com.foregg.presentation.ui.main.home.challenge

import com.foregg.domain.model.enums.ChallengeStatusType
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
    val myChallengeList: StateFlow<MyChallengeListState>,
    val weekOfMonth: StateFlow<String>,
    val isParticipate: StateFlow<Boolean>,
    val btnDayState: StateFlow<List<ChallengeStatusType>>
): PageState

data class MyChallengeListState(
    val isLoaded: Boolean = false,
    val data: List<MyChallengeListItemVo> = emptyList()
)