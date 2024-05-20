package com.foregg.presentation.ui.dailyRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foregg.data.dto.dailyRecord.DailyRecordResponseItem
import com.foregg.domain.model.response.DailyRecordResponseVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.domain.usecase.dailyRecord.GetDailyRecordUseCase
import com.foregg.domain.usecase.dailyRecord.PostDailyRecordUseCase
import com.foregg.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyRecordViewModel @Inject constructor(
    private val postDailyRecordUseCase: PostDailyRecordUseCase,
    private val getDailyRecordUseCase: GetDailyRecordUseCase,
) : BaseViewModel<DailyRecordPageState>() {
    private val dailyRecordListStateFlow: MutableStateFlow<List<DailyRecordResponseItemVo>> = MutableStateFlow( emptyList() )

    override val uiState: DailyRecordPageState = DailyRecordPageState (
        dailyRecordList = dailyRecordListStateFlow.asStateFlow()
    )

    init {
//        getDailyRecord()
    }

    private fun getDailyRecord() {
        viewModelScope.launch {
            getDailyRecordUseCase.invoke(Unit).collect {
                resultResponse(it, ::handleGetDailyRecordSuccess)
            }
        }
    }

    private fun handleGetDailyRecordSuccess(result: DailyRecordResponseVo) {
        viewModelScope.launch {
            dailyRecordListStateFlow.update { result.dailyResponseDto }
        }
    }

    private fun createDailyRecord() {
        viewModelScope.launch {
//            postDailyRecordUseCase.invoke()
        }
    }
}