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
    private val allMyChallengeItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val currentMyChallengeItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val challengeItemListStateFlow: MutableStateFlow<List<ChallengeCardVo>> = MutableStateFlow(emptyList())
    private val challengeMonthWeekStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val myChallengeListStateFlow: MutableStateFlow<List<MyChallengeListItemVo>> = MutableStateFlow(emptyList())
    private val participateBtnBackgroundStateFlow: MutableStateFlow<Int> = MutableStateFlow(R.drawable.bg_rectangle_filled_main_radius_8)
    private val participateBtnTextStateFlow: MutableStateFlow<String> = MutableStateFlow(resourceProvider.getString(R.string.challenge_participate))
    private val participateBtnTextColorStateFlow: MutableStateFlow<Int> = MutableStateFlow(resourceProvider.getColor(R.color.white))
    private val weekOfMonthStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: ChallengePageState = ChallengePageState(
        challengeTapType = challengeTapTypeStateFlow.asStateFlow(),
        allItemCount = allItemCountStateFlow.asStateFlow(),
        currentItemCount = currentItemCountStateFlow.asStateFlow(),
        challengeList = challengeItemListStateFlow.asStateFlow(),
        challengeMonthWeek = challengeMonthWeekStateFlow.asStateFlow(),
        myChallengeList = myChallengeListStateFlow.asStateFlow(),
        allMyChallengeItemCount = allMyChallengeItemCountStateFlow.asStateFlow(),
        currentMyChallengeItemCount = currentMyChallengeItemCountStateFlow.asStateFlow(),
        participateBtnBackground = participateBtnBackgroundStateFlow.asStateFlow(),
        participateBtnText = participateBtnTextStateFlow.asStateFlow(),
        participateBtnTextColor = participateBtnTextColorStateFlow.asStateFlow(),
        weekOfMonth = weekOfMonthStateFlow.asStateFlow()
    )

    fun setView() {
        getAllChallenge()
        getMyChallenge()
    }

    fun onClickParticipateBtn() {
        emitEventFlow(ChallengeEvent.onClickParticipateBtn)
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
                setBtnBackground(0)
                setBtnText(0)
                setBtnTextColor(0)
            } else if (allItemCountStateFlow.value == 0) { currentItemCountStateFlow.update { 0 } }
            else {
                setBtnBackground(currentItemCountStateFlow.value - 1)
                setBtnText(currentItemCountStateFlow.value - 1)
                setBtnTextColor(currentItemCountStateFlow.value - 1)
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
            allMyChallengeItemCountStateFlow.update { result.size }
            if (allMyChallengeItemCountStateFlow.value != 0) {
                if (currentMyChallengeItemCountStateFlow.value == -1) { currentMyChallengeItemCountStateFlow.update { 1 } }
                weekOfMonthStateFlow.update { myChallengeListStateFlow.value[0].weekOfMonth }
            }
            else if (allMyChallengeItemCountStateFlow.value == 0) { currentMyChallengeItemCountStateFlow.update { 0 } }
        }
    }

    fun swipeNextItem() {
        when(challengeTapTypeStateFlow.value) {
            ChallengeTapType.ALL -> {
                increaseAllItemCount()
            }
            ChallengeTapType.MY -> {
                increaseMyItemCount()
            }
        }
    }

    private fun increaseAllItemCount() {
        viewModelScope.launch {
            currentItemCountStateFlow.update {
                if (currentItemCountStateFlow.value < allItemCountStateFlow.value) currentItemCountStateFlow.value + 1 else return@launch
            }
            setBtnBackground(currentItemCountStateFlow.value - 1)
            setBtnText(currentItemCountStateFlow.value - 1)
            setBtnTextColor(currentItemCountStateFlow.value - 1)
        }
    }

    private fun increaseMyItemCount() {
        viewModelScope.launch {
            currentMyChallengeItemCountStateFlow.update {
                if (currentMyChallengeItemCountStateFlow.value < allMyChallengeItemCountStateFlow.value) currentMyChallengeItemCountStateFlow.value + 1 else return@launch
            }
        }
    }

    private fun setBtnBackground(currentIdx: Int) {
        viewModelScope.launch {
            participateBtnBackgroundStateFlow.update {
                if (challengeItemListStateFlow.value[currentIdx].ifMine) {
                    R.drawable.bg_rectangle_filled_white_stroke_main_radius_8
                } else {
                    R.drawable.bg_rectangle_filled_main_radius_8
                }
            }
        }
    }

    private fun setBtnText(currentIdx: Int) {
        viewModelScope.launch {
            participateBtnTextStateFlow.update {
                if (challengeItemListStateFlow.value[currentIdx].ifMine) {
                    resourceProvider.getString(R.string.challenge_participate_already)
                } else {
                    resourceProvider.getString(R.string.challenge_participate)
                }
            }
        }
    }

    private fun setBtnTextColor(currentIdx: Int) {
        viewModelScope.launch {
            participateBtnTextColorStateFlow.update {
                if (challengeItemListStateFlow.value[currentIdx].ifMine) {
                    resourceProvider.getColor(R.color.gs_70)
                } else {
                    resourceProvider.getColor(R.color.white)
                }
            }
        }
    }

    fun swipePreviousItem() {
        when(challengeTapTypeStateFlow.value) {
            ChallengeTapType.ALL -> {
                decreaseAllItemCount()
            }
            ChallengeTapType.MY -> {
                decreaseMyItemCount()
            }
        }
    }

    private fun decreaseAllItemCount() {
        viewModelScope.launch {
            currentItemCountStateFlow.update {
                if (currentItemCountStateFlow.value > 1) currentItemCountStateFlow.value - 1 else return@launch
            }
            setBtnBackground(currentItemCountStateFlow.value - 1)
            setBtnText(currentItemCountStateFlow.value - 1)
            setBtnTextColor(currentItemCountStateFlow.value - 1)
        }
    }

    private fun decreaseMyItemCount() {
        viewModelScope.launch {
            currentMyChallengeItemCountStateFlow.update {
                if (currentMyChallengeItemCountStateFlow.value > 1) currentMyChallengeItemCountStateFlow.value - 1 else return@launch
            }
        }
    }

    fun updateTabType() {
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
                resultResponse(it, ::handleParticipateChallengeSuccess)
            }
        }
    }

    private fun handleParticipateChallengeSuccess(result: Unit) {
        getAllChallenge()
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
        val currentItemId = myChallengeListStateFlow.value[currentMyChallengeItemCountStateFlow.value - 1].id
        viewModelScope.launch {
            completeChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, ::handleCompleteChallengeSuccess)
            }
        }
    }

    private fun handleCompleteChallengeSuccess(result: Unit) {
        //TODO 각 챌린지에 대해 일요일 ~ 토요일 중 어느 요일에 성공을 했는지, 오늘은 무슨 요일인지 데이터 필요
    }
}