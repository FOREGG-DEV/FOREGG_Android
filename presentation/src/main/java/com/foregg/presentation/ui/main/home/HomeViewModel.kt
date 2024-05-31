package com.foregg.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.usecase.home.GetHomeUseCase
import com.foregg.domain.usecase.home.challenge.CompleteChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetMyChallengeUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getMyChallengeUseCase: GetMyChallengeUseCase,
    private val completeChallengeUseCase: CompleteChallengeUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<HomePageState>() {
    private val hasDailyRecordStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val userNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayScheduleStateFlow: MutableStateFlow<List<HomeRecordResponseVo>> = MutableStateFlow(emptyList())
    private val formattedTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val challengeListStateFlow: MutableStateFlow<List<MyChallengeListItemVo>> = MutableStateFlow(emptyList())
    private val scheduleStartPositionStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val homeIntroductionItemListStateFlow: MutableStateFlow<List<Int>> = MutableStateFlow(listOf(R.drawable.ic_card_inrtoduction, R.drawable.ic_card_inrtoduction, R.drawable.ic_card_inrtoduction))
    val month = org.threeten.bp.LocalDate.now().monthValue
    val day = org.threeten.bp.LocalDate.now().dayOfMonth

    override val uiState: HomePageState = HomePageState(
        hasDailyRecord = hasDailyRecordStateFlow.asStateFlow(),
        userName = userNameStateFlow.asStateFlow(),
        todayDate = todayDateStateFlow.asStateFlow(),
        todayScheduleList = todayScheduleStateFlow.asStateFlow(),
        formattedText = formattedTextStateFlow.asStateFlow(),
        challengeList = challengeListStateFlow.asStateFlow(),
        scheduleStartPosition = scheduleStartPositionStateFlow.asStateFlow(),
        homeIntroductionItemList = homeIntroductionItemListStateFlow.asStateFlow()
    )

    fun initScheduleStates() {
        getTodaySchedule()
        getMyChallenge()
    }

    private fun getTodaySchedule() {
        viewModelScope.launch() {
            getHomeUseCase(Unit).collect {
                resultResponse(it, ::handleInitScheduleStatesSuccess, { ForeggLog.D("실패") })
            }
        }
    }

    private fun getMyChallenge() {
        viewModelScope.launch {
            getMyChallengeUseCase(Unit).collect { it ->
                resultResponse(it, { updateChallengeList(it) })
            }
        }
    }

    private fun handleInitScheduleStatesSuccess(result: HomeResponseVo) {
        viewModelScope.launch {
            userNameStateFlow.update { result.userName }
            todayDateStateFlow.update { result.todayDate }
            todayScheduleStateFlow.update { splitTodayScheduleByRepeatedTime(result.homeRecordResponseVo) }
            formattedTextStateFlow.update { resourceProvider.getString(R.string.today_schedule_format, userNameStateFlow.value, month, day) }
            if (todayScheduleStateFlow.value.isNotEmpty()) scheduleStartPositionStateFlow.update { calculatePosition(todayScheduleStateFlow.value) }
        }
    }

    private fun calculatePosition(list: List<HomeRecordResponseVo>): Int {
        var position = 0
        val currentTime = org.threeten.bp.LocalTime.now().hour
        for (i in list.indices) {
            val time = list[i].times.first().split(":").first().toInt()
            if (time > currentTime) {
                position = i
                break
            }
        }
        return position
    }

    private fun updateChallengeList(newList: List<MyChallengeListItemVo>) {
        viewModelScope.launch {
            challengeListStateFlow.update { newList }
        }
    }

    private fun splitTodayScheduleByRepeatedTime(currentList: List<HomeRecordResponseVo>): List<HomeRecordResponseVo> {
        val newList = mutableListOf<HomeRecordResponseVo>()
        for(list in currentList) {
            val subList = splitListItem(list)
            newList.addAll(subList)
        }
        return newList.sortedBy { it.times.first() }
    }

    private fun splitListItem(list: HomeRecordResponseVo): List<HomeRecordResponseVo> {
        return list.times.map { repeatTime ->
            list.copy(times = listOf(repeatTime))
        }
    }

    fun completeChallenge(id: Long) {
        viewModelScope.launch {
            completeChallengeUseCase(id).collect {
                resultResponse(it, { getMyChallenge() })
            }
        }
    }

    fun onClickDailyRecord() {
        viewModelScope.launch {
            hasDailyRecordStateFlow.update { false }
        }
        emitEventFlow(HomeEvent.GoToDailyRecordEvent)
    }

    fun onCLickGoToChallenge() {
        emitEventFlow(HomeEvent.GoToChallengeEvent)
    }
}