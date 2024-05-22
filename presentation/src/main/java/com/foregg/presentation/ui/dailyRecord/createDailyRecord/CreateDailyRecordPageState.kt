package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class CreateDailyRecordPageState (
    val dailyRecordText: StateFlow<String>,
    val isWorstEmotion: StateFlow<Boolean>,
    val isBadEmotion: StateFlow<Boolean>,
    val isSoSoEmotion: StateFlow<Boolean>,
    val isSmileEmotion: StateFlow<Boolean>,
    val isPerfectEmotion: StateFlow<Boolean>,
    val questionText: StateFlow<String>
): PageState