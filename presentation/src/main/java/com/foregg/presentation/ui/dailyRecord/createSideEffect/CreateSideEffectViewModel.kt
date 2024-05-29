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
    private val hasSideEffectStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val recordAdverseEffectTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val questionTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val contentTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            questionTextStateFlow.update { resourceProvider.getString(R.string.create_adverse_effect_text, UserInfo.info.name) }
        }
    }

    override val uiState: CreateSideEffectPageState = CreateSideEffectPageState(
        hasSideEffect = hasSideEffectStateFlow.asStateFlow(),
        recordAdverseEffectText = recordAdverseEffectTextStateFlow.asStateFlow(),
        questionText = questionTextStateFlow.asStateFlow(),
        contentText = contentTextStateFlow
    )

    fun onTextChanged(input: CharSequence) {
        contentTextStateFlow.update { input.toString() }
    }

    fun updateHasSideEffectState(value :Boolean) {
        viewModelScope.launch {
            hasSideEffectStateFlow.update { value }
        }
    }

    fun onClickBtnNext() {
        if (contentTextStateFlow.value.isEmpty()) emitEventFlow(CreateSideEffectEvent.InSufficientTextEvent)
        else createSideEffect()
    }

    private fun createSideEffect() {
        viewModelScope.launch {
            postSideEffectUseCase(request = CreateSideEffectRequestVo(contentTextStateFlow.value)).collect {
                resultResponse(it, { onClickBtnClose() })
            }
        }
    }

    fun onClickBtnClose() {
        emitEventFlow(CreateSideEffectEvent.PopCreateSideFragment)
    }
}