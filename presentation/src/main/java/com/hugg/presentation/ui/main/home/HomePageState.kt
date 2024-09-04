package com.hugg.presentation.ui.main.home

import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.HomeChallengeViewType
import com.hugg.domain.model.response.HomeRecordResponseVo
import com.hugg.domain.model.response.MyChallengeListItemVo
import com.hugg.domain.model.vo.home.HomeAdCardVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class HomePageState (
    val hasDailyRecord : StateFlow<Boolean>,
    val userName : StateFlow<String>,
    val todayDate : StateFlow<String>,
    val todayScheduleList: StateFlow<List<HomeRecordResponseVo>>,
    val formattedText: StateFlow<String>,
    val challengeList: StateFlow<List<MyChallengeListItemVo>>,
    val challengeViewType : StateFlow<HomeChallengeViewType>,
    val homeIntroductionItemList: StateFlow<List<HomeAdCardVo>>,
    val genderType: GenderType,
    val dailyConditionImage: StateFlow<Int>,
    val dailyContent: StateFlow<String>,
    val medicalRecord: StateFlow<String>,
    val medicalRecordId: StateFlow<Long>
) : PageState