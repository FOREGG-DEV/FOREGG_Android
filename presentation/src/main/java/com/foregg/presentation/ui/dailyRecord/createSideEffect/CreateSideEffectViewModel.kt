package com.foregg.presentation.ui.dailyRecord.createSideEffect

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.foregg.domain.usecase.dailyRecord.PostSideEffectUseCase
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
class CreateSideEffectViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val postSideEffectUseCase: PostSideEffectUseCase
) : BaseViewModel<CreateSideEffectPageState>() {
    private val unExistAdverseEffectStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val existAdverseEffectStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val recordAdverseEffectTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val questionTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            questionTextStateFlow.update { resourceProvider.getString(R.string.create_adverse_effect_text, UserInfo.info.name) }
        }
    }

    override val uiState: CreateSideEffectPageState = CreateSideEffectPageState(
        unExistAdverseEffect = unExistAdverseEffectStateFlow.asStateFlow(),
        existAdverseEffect = existAdverseEffectStateFlow.asStateFlow(),
        recordAdverseEffectText = recordAdverseEffectTextStateFlow.asStateFlow(),
        questionText = questionTextStateFlow.asStateFlow()
    )

    fun updateUnExistAdverseEffectState() {
        viewModelScope.launch {
            if (!unExistAdverseEffectStateFlow.value) {
                unExistAdverseEffectStateFlow.update { true }
                existAdverseEffectStateFlow.update { false }
            } else unExistAdverseEffectStateFlow.update { false }
        }
    }

    fun updateExistAdverseEffectState() {
        viewModelScope.launch {
            if (!existAdverseEffectStateFlow.value) {
                existAdverseEffectStateFlow.update { true }
                unExistAdverseEffectStateFlow.update { false }
            } else existAdverseEffectStateFlow.update { false }
        }
    }

    fun onClickBtnNext() {
        emitEventFlow(CreateSideEffectEvent.GetStringContent)
    }

    fun updateRecordAdverseEffectText(content: String) {
        viewModelScope.launch {
            recordAdverseEffectTextStateFlow.update { content }
        }
        createSideEffect()
    }

    private fun createSideEffect() {
        viewModelScope.launch {
            postSideEffectUseCase(request = CreateSideEffectRequestVo(recordAdverseEffectTextStateFlow.value)).collect {
                resultResponse(it, { popFragment() })
            }
        }
    }

    private fun popFragment() {
        emitEventFlow(CreateSideEffectEvent.PopCreateSideFragment)
    }
}