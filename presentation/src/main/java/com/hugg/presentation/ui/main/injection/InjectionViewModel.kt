package com.hugg.presentation.ui.main.injection

import androidx.lifecycle.viewModelScope
import com.hugg.data.base.StatusCode
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import com.hugg.domain.usecase.dailyRecord.GetInjectionInfoUseCase
import com.hugg.domain.usecase.dailyRecord.PostShareInjectionUseCase
import com.hugg.presentation.base.BaseViewModel
import com.hugg.presentation.util.ForeggAnalytics
import com.hugg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class InjectionViewModel @Inject constructor(
    private val shareInjectionUseCase: PostShareInjectionUseCase,
    private val getInjectionInfoUseCase: GetInjectionInfoUseCase
) : BaseViewModel<InjectionPageState>() {

    private val dateStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val timeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val injectionStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val imageStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val explainStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: InjectionPageState = InjectionPageState(
        date = dateStateFlow.asStateFlow(),
        time = timeStateFlow.asStateFlow(),
        injection = injectionStateFlow.asStateFlow(),
        image = imageStateFlow.asStateFlow(),
        explain = explainStateFlow.asStateFlow()
    )

    private var id by Delegates.notNull<Long>()
    private lateinit var time : String

    fun initView(id : Long, time : String){
        ForeggAnalytics.logEvent("injection_alarm", "InjectionFragment")
        this.id = id
        this.time = time
        val request = InjectionAlarmRequestVo(id = id, time = time)
        viewModelScope.launch {
            getInjectionInfoUseCase(request).collect{
                resultResponse(it, ::handleGetDetailSuccess)
            }
        }
    }

    private fun handleGetDetailSuccess(result : InjectionInfoResponseVo){
        viewModelScope.launch {
            val today = TimeFormatter.getToday()
            dateStateFlow.update { TimeFormatter.getDotsDate(today) }
            timeStateFlow.update { result.time }
            injectionStateFlow.update { result.name }
            imageStateFlow.update { result.image }
            explainStateFlow.update { result.description }
        }
    }

    fun onClickHome(){
        emitEventFlow(InjectionEvent.GoToHomeEvent)
    }

    fun onClickShare(){
        ForeggAnalytics.logEvent("injection_share", "InjectionFragment")
        val request = InjectionAlarmRequestVo(id = id, time = time)
        viewModelScope.launch {
            shareInjectionUseCase(request).collect{
                resultResponse(it, { handleShareSuccess() }, ::handleShareError)
            }
        }
    }

    private fun handleShareSuccess(){
        emitEventFlow(InjectionEvent.SuccessShareToast)
    }

    private fun handleShareError(error : String){
        when(error){
            StatusCode.DAILY.SPOUSE_NOT_FOUND -> emitEventFlow(InjectionEvent.ErrorShareToast)
        }
    }
}