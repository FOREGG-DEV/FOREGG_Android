package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.model.request.dailyRecord.EditDailyRecordRequestVo
import com.foregg.domain.usecase.dailyRecord.EditDailyRecordUseCase
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
    private val postDailyRecordUseCase: PostDailyRecordUseCase,
    private val editDailyRecordUseCase: EditDailyRecordUseCase
): BaseViewModel<CreateDailyRecordPageState>() {

    private val dailyRecordTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isSelectedEmotionStateFlow: MutableStateFlow<DailyConditionType> = MutableStateFlow(DailyConditionType.DEFAULT)
    private val questionTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private var contentTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val dailyRecordIdStateFlow: MutableStateFlow<Long> = MutableStateFlow(-1L)
    private val isEditModeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: CreateDailyRecordPageState = CreateDailyRecordPageState(
        dailyRecordText = dailyRecordTextStateFlow.asStateFlow(),
        questionText = questionTextStateFlow.asStateFlow(),
        isSelectedEmotion = isSelectedEmotionStateFlow.asStateFlow(),
        contentText = contentTextStateFlow,
        isEditMode = isEditModeStateFlow.asStateFlow(),
    )

    init {
        viewModelScope.launch {
            questionTextStateFlow.update {
                resourceProvider.getString(R.string.create_daily_record_text, UserInfo.info.name)
            }
        }
    }

    fun setDailyRecordEditData(id: Long, content: String, dailyConditionType: DailyConditionType) {
        if (id == -1L) return
        viewModelScope.launch {
            contentTextStateFlow.update { content }
            dailyRecordIdStateFlow.update { id }
            onClickBtnDailyCondition(dailyConditionType)
            isEditModeStateFlow.update { true }
        }
    }

    fun onTextChanged(input: CharSequence) {
        contentTextStateFlow.update { input.toString() }
    }

    fun onClickBtnDailyCondition(type: DailyConditionType) {
        viewModelScope.launch {
            isSelectedEmotionStateFlow.update { type }
        }
    }

    fun onClickBtnNext() {
        if (contentTextStateFlow.value.isEmpty()) emitEventFlow(CreateDailyRecordEvent.InsufficientTextDataEvent)
        else {
            if (dailyRecordIdStateFlow.value == -1L) {
                createDailyRecord(contentTextStateFlow.value)
            } else {
                editDailyRecord(contentTextStateFlow.value, dailyRecordIdStateFlow.value)
            }
        }
    }

    private fun createDailyRecord(content: String) {
        if (isSelectedEmotionStateFlow.value == DailyConditionType.DEFAULT) {
            emitEventFlow(CreateDailyRecordEvent.InsufficientEmotionDataEvent)
            return
        } else {
            viewModelScope.launch {
                postDailyRecordUseCase(request = CreateDailyRecordRequestVo(isSelectedEmotionStateFlow.value, content)).collect {
                    resultResponse(it, { emitEventFlow(CreateDailyRecordEvent.GoToCreateSideEffectEvent) }, ::handleCreateDailyRecordError)
                }
            }
        }
    }

    private fun handleCreateDailyRecordError(error: String) {
        when (error) {
            StatusCode.DAILY_RECORD.EXIST_DAILY_RECORD -> emitEventFlow(CreateDailyRecordEvent.ExistDailyRecordEvent)
        }
    }

    private fun editDailyRecord(content: String, id: Long) {
        if (isSelectedEmotionStateFlow.value == DailyConditionType.DEFAULT) {
            emitEventFlow(CreateDailyRecordEvent.InsufficientEmotionDataEvent)
            return
        } else {
            viewModelScope.launch {
                editDailyRecordUseCase.invoke(request = EditDailyRecordRequestVo(id,  CreateDailyRecordRequestVo(isSelectedEmotionStateFlow.value, content))).collect {
                    resultResponse(it, { emitEventFlow(CreateDailyRecordEvent.SuccessEditDailyRecordEvent) } )
                }
            }
        }
    }

    fun onClickBtnClose() {
        emitEventFlow(CreateDailyRecordEvent.OnClickBtnClose)
    }
}