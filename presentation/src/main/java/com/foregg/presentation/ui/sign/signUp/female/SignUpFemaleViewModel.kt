package com.foregg.presentation.ui.sign.signUp.female

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.foregg.domain.model.request.sign.SignUpRequestVo
import com.foregg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.model.vo.UserVo
import com.foregg.domain.usecase.auth.PostJoinUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.UserInfo
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFemaleViewModel @Inject constructor(
    private val postJoinUseCase: PostJoinUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : BaseViewModel<SignUpFemalePageState>() {

    companion object{
        const val MIN_ROUND = -1
        const val MAX_ROUND = 100
    }

    private val selectedSurgeryTypeStateFlow : MutableStateFlow<SurgeryType> = MutableStateFlow(SurgeryType.THINK_SURGERY)
    private val progressRoundStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val emptyTextStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val startTreatmentDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val shareCodeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isExpandStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SignUpFemalePageState = SignUpFemalePageState(
        selectedSurgeryType = selectedSurgeryTypeStateFlow.asStateFlow(),
        progressRound = progressRoundStateFlow.asStateFlow(),
        emptyText = emptyTextStateFlow.asStateFlow(),
        startTreatmentDay = startTreatmentDayStateFlow.asStateFlow(),
        shareCode = shareCodeStateFlow.asStateFlow(),
        isExpand = isExpandStateFlow.asStateFlow()
    )

    private lateinit var accessToken : String
    private lateinit var ssn : String

    fun onClickBack(){
        emitEventFlow(SignUpFemaleEvent.GoToBackEvent)
    }

    fun getSurgeryType(args: SignUpFemaleFragmentArgs){
        accessToken = args.accessToken
        this.ssn = args.ssn
        viewModelScope.launch {
            shareCodeStateFlow.update { args.shareCode }
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
        if(selectedSurgeryTypeStateFlow.value != SurgeryType.THINK_SURGERY && startTreatmentDayStateFlow.value.isEmpty()){
            emitEventFlow(SignUpFemaleEvent.ErrorEmptyDate)
            return
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = getRequest(token)
            viewModelScope.launch {
                postJoinUseCase(request).collect{
                    resultResponse(it, ::handleLoginSuccess, { ForeggLog.D("실패") })
                }
            }
        }
    }

    private fun handleLoginSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            saveForeggAccessTokenAndRefreshTokenUseCase(request).collect{
                if(it) getMyInfo() else ForeggLog.D("저장 실패")
            }
        }
    }

    private fun getMyInfo(){
        viewModelScope.launch {
            getMyInfoUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetMyInfo, {ForeggLog.D("오류")})
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        val vo = UserVo(name = result.nickName, ssn = result.ssn, genderType = result.genderType, spouse = result.spouse)
        UserInfo.updateInfo(vo)
        goToMain()
    }

    private fun goToMain(){
        emitEventFlow(SignUpFemaleEvent.GoToMainEvent)
    }

    private fun getRequest(fcmToken : String) : SignUpWithTokenRequestVo {
        return SignUpWithTokenRequestVo(
            accessToken = accessToken,
            signUpRequestVo = SignUpRequestVo(
                surgeryType = selectedSurgeryTypeStateFlow.value,
                count = if(selectedSurgeryTypeStateFlow.value == SurgeryType.THINK_SURGERY) null
                        else progressRoundStateFlow.value,
                startAt = if(selectedSurgeryTypeStateFlow.value == SurgeryType.THINK_SURGERY) null
                        else startTreatmentDayStateFlow.value,
                spouseCode = shareCodeStateFlow.value,
                ssn = ssn,
                fcmToken = fcmToken
            )
        )
    }

    fun onClickCopy(){
        emitEventFlow(SignUpFemaleEvent.OnClickCopyCodeEvent(shareCodeStateFlow.value))
    }
}