package com.foregg.presentation.ui.main.home

import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class HomePageState (
    val hasDailyRecord : StateFlow<Boolean>,
    val userName : StateFlow<String>,
    val todayDate : StateFlow<String>,
    val todayScheduleList: StateFlow<List<HomeRecordResponseVo>>,
    val formattedText: StateFlow<String>
) : PageState