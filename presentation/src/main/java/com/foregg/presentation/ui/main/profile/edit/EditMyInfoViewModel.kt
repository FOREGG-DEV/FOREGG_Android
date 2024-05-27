package com.foregg.presentation.ui.main.profile.edit

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import com.foregg.domain.model.response.ShareCodeResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.usecase.auth.GetShareCodeUseCase
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.domain.usecase.profile.PutEditMyInfoUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val putEditMyInfoUseCase: PutEditMyInfoUseCase,
) : BaseViewModel<EditMyInfoPageState>() {

    companion object{
        const val MIN_ROUND = -1
        const val MAX_ROUND = 100
    }

    private val selectedSurgeryTypeStateFlow : MutableStateFlow<SurgeryType> = MutableStateFlow(SurgeryType.IN_VITRO_FERTILIZATION)
    private val progressRoundStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val startTreatmentDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isExpandStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: EditMyInfoPageState = EditMyInfoPageState(
        selectedSurgeryType = selectedSurgeryTypeStateFlow.asStateFlow(),
        progressRound = progressRoundStateFlow.asStateFlow(),
        startTreatmentDay = startTreatmentDayStateFlow.asStateFlow(),
        shareCode = shareCodeStateFlow.asStateFlow(),
        isExpand = isExpandStateFlow.asStateFlow()
    )

    fun onClickBack(){
        emitEventFlow(EditMyInfoEvent.GoToBackEvent)
    }

    fun getMyInfo(){
        viewModelScope.launch {
            getMyInfoUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        viewModelScope.launch {
            selectedSurgeryTypeStateFlow.update { result.surgeryType }
            progressRoundStateFlow.update { result.round }
            startTreatmentDayStateFlow.update { result.startDate }
            shareCodeStateFlow.update { result.shareCode }
        }
    }

    private fun handleSuccessGetShareCode(result : ShareCodeResponseVo){
        viewModelScope.launch {
            shareCodeStateFlow.update { result.shareCode }
        }
    }

    fun onClickSpinner(){
        viewModelScope.launch {
            isExpandStateFlow.update { !isExpandStateFlow.value }
        }
    }

    fun updateSelectedSurgeryType(type : SurgeryType){
        viewModelScope.launch {
            selectedSurgeryTypeStateFlow.update { type }
        }
        onClickSpinner()
    }

    fun onClickMinus(){
        val round = progressRoundStateFlow.value - 1
        if(round != MIN_ROUND){
            updateProgressRound(round)
        }
    }

    fun onClickPlus(){
        val round = progressRoundStateFlow.value + 1
        if(round != MAX_ROUND){
            updateProgressRound(round)
        }
    }

    private fun updateProgressRound(round : Int){
        viewModelScope.launch {
            progressRoundStateFlow.update { round }
        }
    }

    fun onClickCalendar(){
        emitEventFlow(EditMyInfoEvent.ShowDatePickerDialogEvent)
    }

    fun updateStartTreatmentDay(day : String){
        viewModelScope.launch {
            startTreatmentDayStateFlow.update { day }
        }
    }

    fun onClickEdit(){
        val request = getRequest()
        viewModelScope.launch {
            putEditMyInfoUseCase(request).collect{
                resultResponse(it, {onClickBack()})
            }
        }
    }

    private fun getRequest() : EditMyInfoRequestVo {
        return EditMyInfoRequestVo(
            surgeryType = selectedSurgeryTypeStateFlow.value,
            count = if(selectedSurgeryTypeStateFlow.value == SurgeryType.THINK_SURGERY) null
                    else progressRoundStateFlow.value,
            startDate = if(selectedSurgeryTypeStateFlow.value == SurgeryType.THINK_SURGERY) null
                    else startTreatmentDayStateFlow.value,
        )
    }

    fun onClickCopy(){
        emitEventFlow(EditMyInfoEvent.OnClickCopyCodeEvent(shareCodeStateFlow.value))
    }
}