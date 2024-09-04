package com.hugg.presentation.ui

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.BottomNavType
import com.hugg.domain.model.response.DailyRecordResponseVo
import com.hugg.domain.usecase.dailyRecord.GetDailyRecordUseCase
import com.hugg.presentation.base.BaseViewModel
import com.hugg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getDailyRecordUseCase: GetDailyRecordUseCase,
) : BaseViewModel<MainActivityPageState>() {

    private val pageTypeStateFlow : MutableStateFlow<BottomNavType> = MutableStateFlow(BottomNavType.HOME)

    override val uiState: MainActivityPageState = MainActivityPageState(
        pageType = pageTypeStateFlow.asStateFlow()
    )

    fun onClickCalendar() {
        emitEventFlow(MainActivityEvent.GoToCalendar)
    }

    fun onClickAccount() {
        emitEventFlow(MainActivityEvent.GoToAccount)
    }

    fun onClickHome() {
        emitEventFlow(MainActivityEvent.GoToMain)
    }

    fun onClickInfo() {
        emitEventFlow(MainActivityEvent.GoToInfo)
    }

    fun onClickProfile() {
        emitEventFlow(MainActivityEvent.GoToProfile)
    }

    fun updatePageType(type : BottomNavType){
        viewModelScope.launch {
            pageTypeStateFlow.update { type }
        }
    }

    fun getDailyRecord() {
        viewModelScope.launch {
            getDailyRecordUseCase(Unit).collect {
                resultResponse(it, ::handleSuccessGetDailyRecord)
            }
        }
    }

    private fun handleSuccessGetDailyRecord(vo : DailyRecordResponseVo) {
        if(!vo.dailyResponseDto.any { it.date == TimeFormatter.getToday() }) emitEventFlow(MainActivityEvent.GoToCreateDailyRecord)
    }
}