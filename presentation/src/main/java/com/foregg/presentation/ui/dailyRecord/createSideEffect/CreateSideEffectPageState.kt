package com.foregg.presentation.ui.dailyRecord.createSideEffect

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class CreateSideEffectPageState (
    val unExistAdverseEffect: StateFlow<Boolean>,
    val existAdverseEffect: StateFlow<Boolean>,
    val recordAdverseEffectText: StateFlow<String>,
    val questionText: StateFlow<String>
): PageState