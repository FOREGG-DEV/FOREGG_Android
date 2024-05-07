package com.foregg.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.usecase.home.GetHomeUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : BaseViewModel<HomePageState>() {
    private val hasDailyRecordStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val userNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayScheduleStateFlow: MutableStateFlow<List<HomeRecordResponseVo>> = MutableStateFlow(emptyList())

    override val uiState: HomePageState = HomePageState(
        hasDailyRecord = hasDailyRecordStateFlow.asStateFlow(),
        userName = userNameStateFlow.asStateFlow(),
        todayDate = todayDateStateFlow.asStateFlow(),
        todayScheduleList = todayScheduleStateFlow.asStateFlow()
    )

    fun initScheduleStates() {
        viewModelScope.launch {
            getHomeUseCase(Unit).collect {
                resultResponse(it, ::handleInitScheduleStatesSuccess) { ForeggLog.D("실패") }
            }
        }
    }

    private fun handleInitScheduleStatesSuccess(result: HomeResponseVo) {
        viewModelScope.launch {
            userNameStateFlow.update { result.userName }
            todayDateStateFlow.update { result.todayDate }
            todayScheduleStateFlow.update { result.homeRecordResponseVo }
        }
    }

    fun onClickDailyRecord() {
        viewModelScope.launch {
            hasDailyRecordStateFlow.update { false }
        }
    }
}