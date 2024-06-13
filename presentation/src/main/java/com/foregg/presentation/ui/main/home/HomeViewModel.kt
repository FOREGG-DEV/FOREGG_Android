package com.foregg.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.usecase.home.GetHomeUseCase
import com.foregg.domain.usecase.home.challenge.CompleteChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetMyChallengeUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime
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
    private val husbandNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayScheduleStateFlow: MutableStateFlow<List<HomeRecordResponseVo>> = MutableStateFlow(emptyList())
    private val formattedTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val challengeListStateFlow: MutableStateFlow<List<MyChallengeListItemVo>> = MutableStateFlow(emptyList())
    private val homeIntroductionItemListStateFlow: MutableStateFlow<List<Int>> = MutableStateFlow(listOf(R.drawable.ic_card_inrtoduction, R.drawable.ic_card_inrtoduction, R.drawable.ic_card_inrtoduction))
    private val dailyConditionTypeImageStateFlow: MutableStateFlow<Int> = MutableStateFlow(R.drawable.ic_emotion_perfect_selected)
    private val dailyContentStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val medicalRecordStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val medicalRecordIdStateFlow: MutableStateFlow<Long> = MutableStateFlow(-1)
    val month = org.threeten.bp.LocalDate.now().monthValue
    val day = org.threeten.bp.LocalDate.now().dayOfMonth

    override val uiState: HomePageState = HomePageState(
        hasDailyRecord = hasDailyRecordStateFlow.asStateFlow(),
        userName = userNameStateFlow.asStateFlow(),
        todayDate = todayDateStateFlow.asStateFlow(),
        todayScheduleList = todayScheduleStateFlow.asStateFlow(),
        formattedText = formattedTextStateFlow.asStateFlow(),
        challengeList = challengeListStateFlow.asStateFlow(),
        homeIntroductionItemList = homeIntroductionItemListStateFlow.asStateFlow(),
        genderType = UserInfo.info.genderType,
        dailyConditionImage = dailyConditionTypeImageStateFlow.asStateFlow(),
        dailyContent = dailyContentStateFlow.asStateFlow(),
        medicalRecord = medicalRecordStateFlow.asStateFlow(),
        medicalRecordId = medicalRecordIdStateFlow.asStateFlow()
    )

    fun initScheduleStates() {
        getTodaySchedule()
        if (UserInfo.info.genderType == GenderType.FEMALE) getMyChallenge()
    }

    private fun getTodaySchedule() {
        viewModelScope.launch {
            getHomeUseCase(Unit).collect {
                resultResponse(it, ::handleInitScheduleStatesSuccess)
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
            husbandNameStateFlow.update { result.spouseName }
            todayDateStateFlow.update { result.todayDate }
            todayScheduleStateFlow.update { splitTodayScheduleByRepeatedTime(result.homeRecordResponseVo) }
            dailyConditionTypeImageStateFlow.update { getDailyConditionTypeImage(result.dailyConditionType) }
            dailyContentStateFlow.update { result.dailyContent }
            medicalRecordStateFlow.update { result.latestMedicalRecord }
            medicalRecordIdStateFlow.update { result.medicalRecordId }
            if (UserInfo.info.genderType == GenderType.FEMALE) formattedTextStateFlow.update { resourceProvider.getString(R.string.today_schedule_format, userNameStateFlow.value, month, day) }
            else formattedTextStateFlow.update { resourceProvider.getString(R.string.today_schedule_husband_format, userNameStateFlow.value, husbandNameStateFlow.value, month, day) }
        }
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

    private fun getDailyConditionTypeImage(type: DailyConditionType): Int {
        return when (type) {
            DailyConditionType.WORST -> R.drawable.ic_emotion_worst_selected
            DailyConditionType.BAD -> R.drawable.ic_emotion_bad_selected
            DailyConditionType.SOSO -> R.drawable.ic_emotion_soso_selected
            DailyConditionType.GOOD -> R.drawable.ic_emotion_smile_selected
            DailyConditionType.PERFECT -> R.drawable.ic_emotion_perfect_selected
            DailyConditionType.DEFAULT -> R.drawable.ic_emotion_perfect_selected
        }
    }

    fun onClickBtnMedicalRecord() {
        if (medicalRecordStateFlow.value.isEmpty()) emitEventFlow(HomeEvent.GoToCalendarEvent)
        else emitEventFlow(HomeEvent.GoToCreateEditScheduleEvent)
    }

    fun calculatePosition(list: List<HomeRecordResponseVo>): Int {
        var position = - 1
        val currentHour = LocalTime.now().hour
        val currentMinute = LocalTime.now().minute
        for (i in list.indices) {
            val timeParts = list[i].times.first().split(":")
            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()
            if (hour > currentHour || (hour == currentHour && minute >= currentMinute)) {
                position = i
                break
            }
        }
        return position
    }
}