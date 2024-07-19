package com.foregg.presentation.ui.dailyRecord.createSideEffect

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateSideEffectPageState (
    val hasSideEffect: StateFlow<Boolean>,
    val recordAdverseEffectText: StateFlow<String>,
    val questionText: StateFlow<String>,
    var contentText: MutableStateFlow<String>
): PageState