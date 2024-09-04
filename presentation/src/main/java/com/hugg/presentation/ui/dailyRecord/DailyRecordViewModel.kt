package com.hugg.presentation.ui.dailyRecord

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.DailyRecordTabType
import com.hugg.domain.model.request.dailyRecord.PutEmotionVo
import com.hugg.domain.model.response.DailyRecordResponseVo
import com.hugg.domain.model.response.SideEffectListItemVo
import com.hugg.domain.model.vo.DailyRecordResponseItemVo
import com.hugg.domain.usecase.dailyRecord.DeleteDailyRecordUseCase
import com.hugg.domain.usecase.dailyRecord.DeleteSideEffectUseCase
import com.hugg.domain.usecase.dailyRecord.GetDailyRecordUseCase
import com.hugg.domain.usecase.dailyRecord.GetSideEffectUseCase
import com.hugg.domain.usecase.dailyRecord.PutEmotionUseCase
import com.hugg.presentation.base.BaseViewModel
import com.hugg.presentation.util.ForeggAnalytics
import com.hugg.presentation.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyRecordViewModel @Inject constructor(
    private val getDailyRecordUseCase: GetDailyRecordUseCase,
    private val getSideEffectUseCase: GetSideEffectUseCase,
    private val putEmotionUseCase: PutEmotionUseCase,
    private val deleteDailyRecordUseCase: DeleteDailyRecordUseCase,
    private val deleteSideEffectUseCase: DeleteSideEffectUseCase,
) : BaseViewModel<DailyRecordPageState>() {
    private val dailyRecordListStateFlow: MutableStateFlow<List<DailyRecordResponseItemVo>> = MutableStateFlow( emptyList() )
    private val sideEffectListStateFlow: MutableStateFlow<List<SideEffectListItemVo>> = MutableStateFlow(emptyList())
    private val dailyRecordTabTypeStateFlow: MutableStateFlow<DailyRecordTabType> = MutableStateFlow(DailyRecordTabType.DAILY_RECORD)
    val genderType = UserInfo.info.genderType

    override val uiState: DailyRecordPageState = DailyRecordPageState (
        dailyRecordList = dailyRecordListStateFlow.asStateFlow(),
        dailyRecordTabType = dailyRecordTabTypeStateFlow.asStateFlow(),
        sideEffectList = sideEffectListStateFlow.asStateFlow()
    )

    fun setView() {
        when(dailyRecordTabTypeStateFlow.value){
            DailyRecordTabType.ADVERSE_EFFECT -> getSideEffect()
            DailyRecordTabType.DAILY_RECORD -> getDailyRecord()
        }
    }

    private fun getSideEffect() {
        viewModelScope.launch {
            getSideEffectUseCase(Unit).collect {
                resultResponse(it, { updateSideEffectList(it) })
            }
        }
    }

    private fun updateSideEffectList(list: List<SideEffectListItemVo>) {
        viewModelScope.launch {
            sideEffectListStateFlow.update { list.reversed() }
        }
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
            dailyRecordListStateFlow.update { result.dailyResponseDto.reversed() }
        }
    }

    fun updateTabType(tabType: DailyRecordTabType) {
        viewModelScope.launch {
            dailyRecordTabTypeStateFlow.update { tabType }
            when (tabType) {
                DailyRecordTabType.ADVERSE_EFFECT -> getSideEffect()
                DailyRecordTabType.DAILY_RECORD -> getDailyRecord()
            }
        }
    }

    fun goToRecord() {
        when(dailyRecordTabTypeStateFlow.value){
            DailyRecordTabType.ADVERSE_EFFECT -> emitEventFlow(DailyRecordEvent.GoToCreateSideEffectEvent)
            DailyRecordTabType.DAILY_RECORD -> emitEventFlow(DailyRecordEvent.GoToCreateDailyRecordEvent)
        }
    }

    fun onClickBtnClose() {
        emitEventFlow(DailyRecordEvent.OnClickBtnClose)
    }

    fun putEmotion(request: PutEmotionVo) {
        viewModelScope.launch {
            putEmotionUseCase.invoke(request).collect {
                resultResponse(it, { handleSuccessPutEmotion() })
            }
        }
    }

    private fun handleSuccessPutEmotion(){
        ForeggAnalytics.logEvent("daily_reaction", "DailyRecordFragment")
        getDailyRecord()
    }

    fun deleteDailyRecord(id: Long) {
        viewModelScope.launch {
            deleteDailyRecordUseCase.invoke(id).collect {
                resultResponse(it, { getDailyRecord() })
            }
        }
    }

    fun deleteSideEffectRecord(id: Long) {
        viewModelScope.launch {
            deleteSideEffectUseCase.invoke(id).collect {
                resultResponse(it, { getSideEffect() })
            }
        }
    }
}