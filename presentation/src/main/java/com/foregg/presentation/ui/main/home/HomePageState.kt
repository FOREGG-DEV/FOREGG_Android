package com.foregg.presentation.ui.main.home

import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class HomePageState (
    val hasDailyRecord : StateFlow<Boolean>,
    val userName : StateFlow<String>,
    val todayDate : StateFlow<String>,
    val todayScheduleList: StateFlow<List<HomeRecordResponseVo>>,
    val formattedText: StateFlow<String>,
    val challengeList: StateFlow<List<MyChallengeListItemVo>>,
    val scheduleStartPosition: StateFlow<Int>,
    val homeIntroductionItemList: StateFlow<List<Int>>,
    val genderType: GenderType,
    val dailyConditionImage: StateFlow<Int>,
    val dailyContent: StateFlow<String>,
    val medicalRecord: StateFlow<String>
) : PageState