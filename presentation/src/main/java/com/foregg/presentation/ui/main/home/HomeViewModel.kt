package com.foregg.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.usecase.home.GetHomeUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel<HomePageState>() {
    private val hasDailyRecordStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val userNameStateFlow: MutableStateFlow<String> = MutableStateFlow("임시 닉네임")
    private val todayDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todayScheduleStateFlow: MutableStateFlow<List<HomeRecordResponseVo>> = MutableStateFlow(emptyList())
    val month = org.threeten.bp.LocalDate.now().monthValue
    val day = org.threeten.bp.LocalDate.now().dayOfMonth
    val formattedText = resourceProvider.getString(R.string.today_schedule_format, userNameStateFlow.value, month, day)

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