package com.foregg.presentation.ui.main.home.challenge

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.ChallengeStatusType
import com.foregg.domain.model.enums.ChallengeTapType
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.usecase.home.challenge.CompleteChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetAllChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetMyChallengeUseCase
import com.foregg.domain.usecase.home.challenge.ParticipateChallengeUseCase
import com.foregg.domain.usecase.home.challenge.QuitChallengeUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.sql.Time
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    private val getAllChallengeUseCase: GetAllChallengeUseCase,
    private val participateChallengeUseCase: ParticipateChallengeUseCase,
    private val quitChallengeUseCase: QuitChallengeUseCase,
    private val completeChallengeUseCase: CompleteChallengeUseCase,
    private val getMyChallengeUseCase: GetMyChallengeUseCase
): BaseViewModel<ChallengePageState>() {
    private val challengeTapTypeStateFlow: MutableStateFlow<ChallengeTapType> = MutableStateFlow(ChallengeTapType.ALL)
    private val allItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val currentItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val challengeItemListStateFlow: MutableStateFlow<List<ChallengeCardVo>> = MutableStateFlow(emptyList())
    private val challengeMonthWeekStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val myChallengeListStateFlow: MutableStateFlow<List<MyChallengeListItemVo>> = MutableStateFlow(emptyList())
    private val weekOfMonthStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isParticipateStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val btnDayStateFlow: MutableStateFlow<List<ChallengeStatusType>> = MutableStateFlow(List(7) { ChallengeStatusType.DEFAULT })

    override val uiState: ChallengePageState = ChallengePageState(
        challengeTapType = challengeTapTypeStateFlow.asStateFlow(),
        allItemCount = allItemCountStateFlow.asStateFlow(),
        currentItemCount = currentItemCountStateFlow.asStateFlow(),
        challengeList = challengeItemListStateFlow.asStateFlow(),
        challengeMonthWeek = challengeMonthWeekStateFlow.asStateFlow(),
        myChallengeList = myChallengeListStateFlow.asStateFlow(),
        weekOfMonth = weekOfMonthStateFlow.asStateFlow(),
        isParticipate = isParticipateStateFlow.asStateFlow(),
        btnDayState = btnDayStateFlow.asStateFlow()
    )

    fun setView() {
        getAllChallenge()
    }

    fun onClickParticipateBtn() {
        participateChallenge()
    }

    fun getAllChallenge() {
        viewModelScope.launch(Dispatchers.Main) {
            getAllChallengeUseCase(request = Unit).collect{
                resultResponse(it, ::handleGetSuccessChallenge)
            }
        }
    }

    private fun handleGetSuccessChallenge(result: List<ChallengeCardVo>) {
        viewModelScope.launch {
            challengeItemListStateFlow.update { result }
            allItemCountStateFlow.update { result.size }
            if (allItemCountStateFlow.value != 0 && currentItemCountStateFlow.value == -1) {
                currentItemCountStateFlow.update { 1 }
                isParticipateStateFlow.update { challengeItemListStateFlow.value[0].ifMine }
            }
            else if (allItemCountStateFlow.value == 0) { currentItemCountStateFlow.update { 0 } }
            else if (allItemCountStateFlow.value != 0 && currentItemCountStateFlow.value != -1){
                isParticipateStateFlow.update { challengeItemListStateFlow.value[currentItemCountStateFlow.value - 1].ifMine }
            }
        }
    }

    fun getMyChallenge() {
        viewModelScope.launch {
            getMyChallengeUseCase(request = Unit).collect {
                resultResponse(it, ::handleGetSuccessMyChallenge)
            }
        }
    }

    private fun handleGetSuccessMyChallenge(result: List<MyChallengeListItemVo>) {
        viewModelScope.launch {
            myChallengeListStateFlow.update { result }
            allItemCountStateFlow.update { result.size }
            if (allItemCountStateFlow.value != 0) {
                if (currentItemCountStateFlow.value == -1) { currentItemCountStateFlow.update { 1 } }
                weekOfMonthStateFlow.update { myChallengeListStateFlow.value[0].weekOfMonth }
                updateBtnDayState(0)
            }
            else if (allItemCountStateFlow.value == 0) { currentItemCountStateFlow.update { 0 } }
        }
    }

    private fun updateBtnDayState(index: Int) {
        val newState = MutableStateFlow(List(7) { ChallengeStatusType.DEFAULT }).value.toMutableList()
        val todayIndex = dayToIndex(TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek))

        for (i in 0 until todayIndex) {
            newState[i] = ChallengeStatusType.FAIL
        }

        viewModelScope.launch {
            myChallengeListStateFlow.value[index].successDays?.let { successDays ->
                for (day in successDays) {
                    val dayIndex = dayToIndex(day)
                    if (dayIndex > todayIndex) break
                    else if (dayIndex != -1) {
                        newState[dayIndex] = ChallengeStatusType.SUCCESS
                    }
                }
            }
            if (newState[todayIndex] == ChallengeStatusType.DEFAULT) newState[todayIndex] = ChallengeStatusType.TODAY
            btnDayStateFlow.update { newState }
            ForeggLog.D(btnDayStateFlow.value.toString() + ", " + myChallengeListStateFlow.value[index].name)
        }
    }

    private fun dayToIndex(day: String): Int {
        return when (day) {
            "일" -> 0
            "월" -> 1
            "화" -> 2
            "수" -> 3
            "목" -> 4
            "금" -> 5
            "토" -> 6
            else -> -1
        }
    }

    fun swipeItem(position: Int, previousPosition: Int) {
        if (position > previousPosition) {
            swipeNextItem()
        }
        else if (position < previousPosition) {
            swipePreviousItem()
        }
    }

    private fun swipeNextItem() {
        viewModelScope.launch {
            currentItemCountStateFlow.update {
                if (currentItemCountStateFlow.value < allItemCountStateFlow.value) currentItemCountStateFlow.value + 1 else return@launch
            }
            if(challengeTapTypeStateFlow.value == ChallengeTapType.ALL) isParticipateStateFlow.update { challengeItemListStateFlow.value[currentItemCountStateFlow.value - 1].ifMine }
            else if (challengeTapTypeStateFlow.value == ChallengeTapType.MY) updateBtnDayState(currentItemCountStateFlow.value - 1)
        }
    }

    private fun swipePreviousItem() {
        viewModelScope.launch {
            currentItemCountStateFlow.update {
                if (currentItemCountStateFlow.value > 1) currentItemCountStateFlow.value - 1 else return@launch
            }
            if(challengeTapTypeStateFlow.value == ChallengeTapType.ALL) isParticipateStateFlow.update { challengeItemListStateFlow.value[currentItemCountStateFlow.value - 1].ifMine }
            else if (challengeTapTypeStateFlow.value == ChallengeTapType.MY) updateBtnDayState(currentItemCountStateFlow.value - 1)
        }
    }

    fun updateTabType(tapType: ChallengeTapType) {
        if (challengeTapTypeStateFlow.value == tapType) return
        viewModelScope.launch {
            challengeTapTypeStateFlow.update {
                if (challengeTapTypeStateFlow.value == ChallengeTapType.ALL) ChallengeTapType.MY else ChallengeTapType.ALL
            }
            currentItemCountStateFlow.update { 1 }
        }
    }

    private fun participateChallenge() {
        val currentIdx = currentItemCountStateFlow.value - 1
        val currentItemId = challengeItemListStateFlow.value[currentIdx].id

        if (challengeItemListStateFlow.value[currentIdx].ifMine) return

        viewModelScope.launch {
            participateChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, { getAllChallenge() })
            }
        }
    }

    fun quitChallenge(id: Long) {
        viewModelScope.launch {
            quitChallengeUseCase(request = id).collect {
                resultResponse(it, { getMyChallenge() })
            }
        }
    }

    fun onClickBtnComplete() {
        emitEventFlow(ChallengeEvent.OnClickBtnComplete)
    }

    fun completeChallenge() {
        val currentItemId = myChallengeListStateFlow.value[currentItemCountStateFlow.value - 1].id
        viewModelScope.launch {
            completeChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, { getMyChallenge() }, null, true)
            }
        }
    }

    fun onClickBtnBack() {
        emitEventFlow(ChallengeEvent.OnClickBtnBack)
    }
}