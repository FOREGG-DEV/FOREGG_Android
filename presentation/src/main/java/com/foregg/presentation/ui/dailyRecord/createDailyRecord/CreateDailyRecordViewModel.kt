package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.enums.EmotionType
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.usecase.dailyRecord.PostDailyRecordUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDailyRecordViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val postDailyRecordUseCase: PostDailyRecordUseCase
): BaseViewModel<CreateDailyRecordPageState>() {
    private val dailyRecordTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isWorstEmotionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isBadEmotionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSoSoEmotionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSmileEmotionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isPerfectEmotionStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val questionTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: CreateDailyRecordPageState = CreateDailyRecordPageState(
        dailyRecordText = dailyRecordTextStateFlow.asStateFlow(),
        isWorstEmotion = isWorstEmotionStateFlow.asStateFlow(),
        isBadEmotion = isBadEmotionStateFlow.asStateFlow(),
        isSoSoEmotion = isSoSoEmotionStateFlow.asStateFlow(),
        isSmileEmotion = isSmileEmotionStateFlow.asStateFlow(),
        isPerfectEmotion = isPerfectEmotionStateFlow.asStateFlow(),
        questionText = questionTextStateFlow.asStateFlow()
    )

    init {
        viewModelScope.launch {
            questionTextStateFlow.update {
                resourceProvider.getString(R.string.create_daily_record_text, UserInfo.info.name)
            }
        }
    }

    fun onClickBtnWorst() {
        if (!isWorstEmotionStateFlow.value) {
            isWorstEmotionStateFlow.update { true }
            isBadEmotionStateFlow.update { false }
            isSoSoEmotionStateFlow.update { false }
            isSmileEmotionStateFlow.update { false }
            isPerfectEmotionStateFlow.update { false }
        } else isWorstEmotionStateFlow.update { false }
    }

    fun onClickBtnBad() {
        if (!isBadEmotionStateFlow.value) {
            isWorstEmotionStateFlow.update { false }
            isBadEmotionStateFlow.update { true }
            isSoSoEmotionStateFlow.update { false }
            isSmileEmotionStateFlow.update { false }
            isPerfectEmotionStateFlow.update { false }
        } else isBadEmotionStateFlow.update { false }
    }

    fun onClickBtnSoSo() {
        if (!isSoSoEmotionStateFlow.value) {
            isWorstEmotionStateFlow.update { false }
            isBadEmotionStateFlow.update { false }
            isSoSoEmotionStateFlow.update { true }
            isSmileEmotionStateFlow.update { false }
            isPerfectEmotionStateFlow.update { false }
        } else isSoSoEmotionStateFlow.update { false }
    }

    fun onClickBtnSmile() {
        if (!isSmileEmotionStateFlow.value) {
            isWorstEmotionStateFlow.update { false }
            isBadEmotionStateFlow.update { false }
            isSoSoEmotionStateFlow.update { false }
            isSmileEmotionStateFlow.update { true }
            isPerfectEmotionStateFlow.update { false }
        } else isSmileEmotionStateFlow.update { false }
    }

    fun onClickBtnPerfect() {
        if (!isPerfectEmotionStateFlow.value) {
            isWorstEmotionStateFlow.update { false }
            isBadEmotionStateFlow.update { false }
            isSoSoEmotionStateFlow.update { false }
            isSmileEmotionStateFlow.update { false }
            isPerfectEmotionStateFlow.update { true }
        } else isPerfectEmotionStateFlow.update { false }
    }

    fun onClickBtnNext() {
        emitEventFlow(CreateDailyRecordEvent.GetDailyRecordDataEvent)
    }

    fun createDailyRecord(content: String) {
        val emotionStateFlows = listOf(
            isWorstEmotionStateFlow to DailyConditionType.WORST,
            isBadEmotionStateFlow to DailyConditionType.BAD,
            isSoSoEmotionStateFlow to DailyConditionType.SOSO,
            isSmileEmotionStateFlow to DailyConditionType.GOOD,
            isPerfectEmotionStateFlow to DailyConditionType.PERFECT
        )
        val trueEmotion = emotionStateFlows.firstOrNull { it.first.value }

        if (trueEmotion == null) {
            emitEventFlow(CreateDailyRecordEvent.InsufficientEmotionDataEvent)
            return
        } else {
            viewModelScope.launch {
                postDailyRecordUseCase(request = CreateDailyRecordRequestVo(trueEmotion.second, content)).collect {
                    resultResponse(it, { emitEventFlow(CreateDailyRecordEvent.GoToCreateSideEffectEvent) })
                }
            }
        }
    }

    fun onClickBtnClose() {
        emitEventFlow(CreateDailyRecordEvent.OnClickBtnClose)
    }
}