package com.foregg.presentation.ui.main.injection

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.usecase.dailyRecord.PostShareInjectionUseCase
import com.foregg.domain.usecase.schedule.GetScheduleDetailUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InjectionViewModel @Inject constructor(
    private val shareInjectionUseCase: PostShareInjectionUseCase,
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase
) : BaseViewModel<InjectionPageState>() {

    private val dateStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val timeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val injectionStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: InjectionPageState = InjectionPageState(
        date = dateStateFlow.asStateFlow(),
        time = timeStateFlow.asStateFlow(),
        injection = injectionStateFlow.asStateFlow()
    )

    fun initView(id : Long){
        viewModelScope.launch {
            getScheduleDetailUseCase(id).collect{
                resultResponse(it, ::handleGetDetailSuccess)
            }
        }
    }

    private fun handleGetDetailSuccess(result : ScheduleDetailVo){
        viewModelScope.launch {
            val today = TimeFormatter.getToday()
            dateStateFlow.update { TimeFormatter.getDotsDate(today) }
            timeStateFlow.update { result.repeatTimes.first().toString() }
            injectionStateFlow.update { result.name }
        }
    }

    fun onClickHome(){
        emitEventFlow(InjectionEvent.GoToHomeEvent)
    }

    fun onClickShare(){
        viewModelScope.launch {
            shareInjectionUseCase(Unit).collect{
                resultResponse(it, { handleShareSuccess() })
            }
        }
    }

    private fun handleShareSuccess(){

    }
}