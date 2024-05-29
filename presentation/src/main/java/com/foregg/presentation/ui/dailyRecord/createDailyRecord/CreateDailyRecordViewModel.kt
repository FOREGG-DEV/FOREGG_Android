package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.DailyRecordTabType
import com.foregg.domain.model.enums.EmotionType
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.usecase.dailyRecord.PostDailyRecordUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ForeggToast
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
    private val isSelectedEmotionStateFlow: MutableStateFlow<DailyConditionType> = MutableStateFlow(DailyConditionType.DEFAULT)
    private val questionTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private var contentTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: CreateDailyRecordPageState = CreateDailyRecordPageState(
        dailyRecordText = dailyRecordTextStateFlow.asStateFlow(),
        questionText = questionTextStateFlow.asStateFlow(),
        isSelectedEmotion = isSelectedEmotionStateFlow.asStateFlow(),
        contentText = contentTextStateFlow
    )

    init {
        viewModelScope.launch {
            questionTextStateFlow.update {
                resourceProvider.getString(R.string.create_daily_record_text, UserInfo.info.name)
            }
        }
    }

    fun onTextChanged(input: CharSequence) {
        ForeggLog.D(input.toString())
        contentTextStateFlow.update { input.toString() }
    }

    fun onClickBtnDailyCondition(type: DailyConditionType) {
        viewModelScope.launch {
            isSelectedEmotionStateFlow.update { type }
        }
    }

    fun onClickBtnNext() {
        if (contentTextStateFlow.value.isEmpty()) emitEventFlow(CreateDailyRecordEvent.InsufficientTextDataEvent)
        else createDailyRecord(contentTextStateFlow.value)
    }

    private fun createDailyRecord(content: String) {
        if (isSelectedEmotionStateFlow.value == DailyConditionType.DEFAULT) {
            emitEventFlow(CreateDailyRecordEvent.InsufficientEmotionDataEvent)
            return
        } else {
            viewModelScope.launch {
                postDailyRecordUseCase(request = CreateDailyRecordRequestVo(isSelectedEmotionStateFlow.value, content)).collect {
                    resultResponse(it, { emitEventFlow(CreateDailyRecordEvent.GoToCreateSideEffectEvent) })
                }
            }
        }
    }

    fun onClickBtnClose() {
        emitEventFlow(CreateDailyRecordEvent.OnClickBtnClose)
    }
}