package com.foregg.presentation.ui.sign.signUp.female

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.domain.model.request.SaveForeggJwtRequestVo
import com.foregg.domain.model.request.SignUpRequestVo
import com.foregg.domain.model.request.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.usecase.auth.PostJoinUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFemaleViewModel @Inject constructor(
    private val postJoinUseCase: PostJoinUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase
) : BaseViewModel<SignUpFemalePageState>() {

    companion object{
        const val MIN_ROUND = -1
        const val MAX_ROUND = 100
    }

    private val surgeryTypeListStateFlow : MutableStateFlow<List<SurgeryType>> = MutableStateFlow(
        emptyList()
    )
    private val selectedSurgeryTypeStateFlow : MutableStateFlow<SurgeryType> = MutableStateFlow(SurgeryType.체외_수정)
    private val progressRoundStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val startTreatmentDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isExpandStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpFemalePageState = SignUpFemalePageState(
        surgeryTypeList = surgeryTypeListStateFlow.asStateFlow(),
        selectedSurgeryType = selectedSurgeryTypeStateFlow.asStateFlow(),
        progressRound = progressRoundStateFlow.asStateFlow(),
        startTreatmentDay = startTreatmentDayStateFlow.asStateFlow(),
        shareCode = shareCodeStateFlow.asStateFlow(),
        isExpand = isExpandStateFlow.asStateFlow()
    )

    private lateinit var accessToken : String

    fun onClickBack(){
        emitEventFlow(SignUpFemaleEvent.GoToBackEvent)
    }

    fun getSurgeryType(token : String){
        accessToken = token
        val list = listOf(SurgeryType.시술_고민_중, SurgeryType.난자_동결, SurgeryType.체외_수정)
        viewModelScope.launch {
            surgeryTypeListStateFlow.update { list }
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
        emitEventFlow(SignUpFemaleEvent.ShowDatePickerDialogEvent)
    }

    fun updateStartTreatmentDay(day : String){
        viewModelScope.launch {
            startTreatmentDayStateFlow.update { day }
        }
    }

    fun onClickJoin(){
        val request = getRequest()
        viewModelScope.launch {
            postJoinUseCase(request).collect{
                resultResponse(it, ::handleLoginSuccess) { ForeggLog.D("실패") }
            }
        }
    }

    private fun handleLoginSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = "")
        viewModelScope.launch {
            saveForeggAccessTokenAndRefreshTokenUseCase(request).collect{
                if(it) goToMain() else ForeggLog.D("저장 실패")
            }
        }
    }

    private fun goToMain(){
        emitEventFlow(SignUpFemaleEvent.GoToMainEvent)
    }

    private fun getRequest() : SignUpWithTokenRequestVo{
        return SignUpWithTokenRequestVo(
            accessToken = accessToken,
            signUpRequestVo = SignUpRequestVo(
                surgeryType = selectedSurgeryTypeStateFlow.value,
                count = if(selectedSurgeryTypeStateFlow.value == SurgeryType.시술_고민_중) null
                        else progressRoundStateFlow.value,
                startAt = if(selectedSurgeryTypeStateFlow.value == SurgeryType.시술_고민_중) null
                        else startTreatmentDayStateFlow.value
            )
        )
    }

    fun onClickCopy(){
        emitEventFlow(SignUpFemaleEvent.OnClickCopyCodeEvent(shareCodeStateFlow.value))
    }
}