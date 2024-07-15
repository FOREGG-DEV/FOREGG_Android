package com.foregg.presentation.ui.main.home.challenge

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.ChallengeStatusType
import com.foregg.domain.model.enums.ChallengeTapType
import com.foregg.domain.model.request.challenge.MarkChallengeVisitRequestVo
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.usecase.home.challenge.CompleteChallengeUseCase
import com.foregg.domain.usecase.home.challenge.DeleteChallengeVisitUseCase
import com.foregg.domain.usecase.home.challenge.DeleteCompleteChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetAllChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetMyChallengeUseCase
import com.foregg.domain.usecase.home.challenge.GetVisitWeekChallengeUseCase
import com.foregg.domain.usecase.home.challenge.MarkChallengeVisitUseCase
import com.foregg.domain.usecase.home.challenge.ParticipateChallengeUseCase
import com.foregg.domain.usecase.home.challenge.QuitChallengeUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    private val getAllChallengeUseCase: GetAllChallengeUseCase,
    private val participateChallengeUseCase: ParticipateChallengeUseCase,
    private val quitChallengeUseCase: QuitChallengeUseCase,
    private val completeChallengeUseCase: CompleteChallengeUseCase,
    private val getMyChallengeUseCase: GetMyChallengeUseCase,
    private val deleteCompleteChallengeUseCase: DeleteCompleteChallengeUseCase,
    private val markChallengeVisitUseCase: MarkChallengeVisitUseCase,
    private val getVisitWeekChallengeUseCase: GetVisitWeekChallengeUseCase,
    private val deleteChallengeVisitUseCase: DeleteChallengeVisitUseCase
): BaseViewModel<ChallengePageState>() {
    private val challengeTapTypeStateFlow: MutableStateFlow<ChallengeTapType> = MutableStateFlow(ChallengeTapType.ALL)
    private val allItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val currentItemCountStateFlow: MutableStateFlow<Int> = MutableStateFlow(-1)
    private val challengeItemListStateFlow: MutableStateFlow<List<ChallengeCardVo>> = MutableStateFlow(emptyList())
    private val challengeMonthWeekStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val myChallengeListStateFlow: MutableStateFlow<MyChallengeListState> = MutableStateFlow(MyChallengeListState())
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

    companion object {
        const val SUN = 0
        const val MON = 1
        const val TUE = 2
        const val WED = 3
        const val THU = 4
        const val FRI = 5
        const val SAT = 6
    }

    private val todayOfWeek = TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek)
    private var position = 0

    fun onClickParticipateBtn() {
        participateChallenge()
    }

    fun getChallengeList(id : Long){
        if(id != (-1).toLong()) getMyChallenge() else getAllChallenge()
    }

    fun getAllChallenge() {
        viewModelScope.launch(Dispatchers.Main) {
            getAllChallengeUseCase(request = Unit).collect{
                resultResponse(it, ::handleGetSuccessChallenge, needLoading = true)
            }
        }
    }

    private fun handleGetSuccessChallenge(result: List<ChallengeCardVo>) {
        viewModelScope.launch {
            challengeItemListStateFlow.update { result }
            allItemCountStateFlow.update { result.size }
            val index = if(currentItemCountStateFlow.value == -1) 0 else currentItemCountStateFlow.value - 1
            ForeggLog.D("하이 $index, ${result[index].ifMine}")
            isParticipateStateFlow.update { result[index].ifMine }
        }
    }

    fun getMyChallenge() {
        viewModelScope.launch {
            getMyChallengeUseCase(request = Unit).collect {
                resultResponse(it, ::handleGetSuccessMyChallenge, needLoading = true)
            }
        }
    }

    private fun handleGetSuccessMyChallenge(result: List<MyChallengeListItemVo>) {
        viewModelScope.launch {
            myChallengeListStateFlow.update { MyChallengeListState(isLoaded = true, data = result) }
            allItemCountStateFlow.update { result.size }
            if (result.isNotEmpty()) {
                weekOfMonthStateFlow.update { myChallengeListStateFlow.value.data[0].weekOfMonth }
                updateBtnDayState(0)
            }
        }
    }

    private fun updateBtnDayState(index: Int) {
        val newState = MutableStateFlow(List(7) { ChallengeStatusType.DEFAULT }).value.toMutableList()
        val todayIndex = dayToIndex(TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek))

        for (i in 0 until todayIndex) {
            newState[i] = ChallengeStatusType.FAIL
        }

        viewModelScope.launch {
            myChallengeListStateFlow.value.data[index].successDays?.let { successDays ->
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
        }
    }

    private fun dayToIndex(day: String): Int {
        return when (day) {
            "일" -> SUN
            "월" -> MON
            "화" -> TUE
            "수" -> WED
            "목" -> THU
            "금" -> FRI
            "토" -> SAT
            else -> -1
        }
    }

    fun swipeItem(position: Int) {
        this.position = position
        updateCurrentItemCount(position + 1)
        if(challengeTapTypeStateFlow.value == ChallengeTapType.MY) getAndMarkVisitChallenge()
        updateAfterSwipeButtonState()
    }

    private fun getAndMarkVisitChallenge() {
        val request = MarkChallengeVisitRequestVo(
            myChallengeListStateFlow.value.data[position].id,
            myChallengeListStateFlow.value.data[position].weekOfMonth,
        )
        viewModelScope.launch {
            val result = getVisitWeekChallengeUseCase(request.id).first()
            markChallengeVisitUseCase(request).first()
            if(result != request.time && result.isNotBlank() && !myChallengeListStateFlow.value.data[position].lastSaturday) emitEventFlow(ChallengeEvent.ShowWeekEndDialog(false))
        }
    }

    private fun updateAfterSwipeButtonState(){
        if(challengeTapTypeStateFlow.value == ChallengeTapType.ALL) isParticipateStateFlow.update { challengeItemListStateFlow.value[currentItemCountStateFlow.value - 1].ifMine }
        else if (challengeTapTypeStateFlow.value == ChallengeTapType.MY) updateBtnDayState(currentItemCountStateFlow.value - 1)
    }

    private fun updateCurrentItemCount(position: Int){
        viewModelScope.launch {
            currentItemCountStateFlow.update { position }
        }
    }

    fun updateTabType(tapType: ChallengeTapType) {
        viewModelScope.launch {
            challengeTapTypeStateFlow.update { tapType }
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
                resultResponse(it, { handleSuccessQuitChallenge(id) })
            }
        }
    }

    private fun handleSuccessQuitChallenge(id : Long){
        viewModelScope.launch {
            deleteChallengeVisitUseCase(id).first()
        }
        getMyChallenge()
    }

    fun onClickBtnComplete(index : Int) {
        if(index == dayToIndex(todayOfWeek) && btnDayStateFlow.value[index] == ChallengeStatusType.SUCCESS) deleteCompleteChallenge()
        else if(index == dayToIndex(todayOfWeek)) emitEventFlow(ChallengeEvent.OnClickBtnComplete)
    }

    fun completeChallenge() {
        val currentItemId = myChallengeListStateFlow.value.data[currentItemCountStateFlow.value - 1].id
        viewModelScope.launch {
            completeChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, { handleSuccessCompleteChallenge() }, needLoading = true)
            }
        }
    }

    private fun handleSuccessCompleteChallenge() {
        if(dayToIndex(todayOfWeek) == SAT) {
            val isSuccess = !btnDayStateFlow.value.any { it == ChallengeStatusType.FAIL }
            emitEventFlow(ChallengeEvent.ShowWeekEndDialog(isSuccess))
        }
        getMyChallenge()
    }

    private fun deleteCompleteChallenge(){
        val currentItemId = myChallengeListStateFlow.value.data[currentItemCountStateFlow.value - 1].id
        viewModelScope.launch {
            deleteCompleteChallengeUseCase(request = currentItemId).collect {
                resultResponse(it, { getMyChallenge() }, needLoading = true)
            }
        }
    }

    fun onClickBtnBack() {
        emitEventFlow(ChallengeEvent.OnClickBtnBack)
    }

    fun getItemPosition(id : Long) : Int {
        val item = myChallengeListStateFlow.value.data.find { it.id == id }
        val position = myChallengeListStateFlow.value.data.indexOf(item)
        updateCurrentItemCount(position + 1)
        return position
    }
}