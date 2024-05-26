package com.foregg.presentation.ui.main.home.challenge

import androidx.lifecycle.viewModelScope
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    override val uiState: ChallengePageState = ChallengePageState(
        challengeTapType = challengeTapTypeStateFlow.asStateFlow(),
        allItemCount = allItemCountStateFlow.asStateFlow(),
        currentItemCount = currentItemCountStateFlow.asStateFlow(),
        challengeList = challengeItemListStateFlow.asStateFlow(),
        challengeMonthWeek = challengeMonthWeekStateFlow.asStateFlow(),
        myChallengeList = myChallengeListStateFlow.asStateFlow(),
        weekOfMonth = weekOfMonthStateFlow.asStateFlow(),
        isParticipate = isParticipateStateFlow.asStateFlow()
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
            }
            else if (allItemCountStateFlow.value == 0) { currentItemCountStateFlow.update { 0 } }
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
        }
    }

    private fun swipePreviousItem() {
        viewModelScope.launch {
            currentItemCountStateFlow.update {
                if (currentItemCountStateFlow.value > 1) currentItemCountStateFlow.value - 1 else return@launch
            }
            if(challengeTapTypeStateFlow.value == ChallengeTapType.ALL) isParticipateStateFlow.update { challengeItemListStateFlow.value[currentItemCountStateFlow.value - 1].ifMine }
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

    fun participateChallenge() {
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
                resultResponse(it, ::handleQuitChallengeSuccess)
            }
        }
    }

    private fun handleQuitChallengeSuccess(result: Unit) {
        getMyChallenge()
    }

    fun completeChallenge() {
        val currentItemId = myChallengeListStateFlow.value[currentItemCountStateFlow.value - 1].id
        viewModelScope.launch {
            completeChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, ::handleCompleteChallengeSuccess)
            }
        }
    }

    private fun handleCompleteChallengeSuccess(result: Unit) {
        //TODO 각 챌린지에 대해 일요일 ~ 토요일 중 어느 요일에 성공을 했는지, 오늘은 무슨 요일인지 데이터 필요
    }

    fun onClickBtnBack() {
        emitEventFlow(ChallengeEvent.OnClickBtnBack)
    }
}