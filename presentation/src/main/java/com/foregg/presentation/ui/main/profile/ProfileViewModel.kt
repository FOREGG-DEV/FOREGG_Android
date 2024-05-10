package com.foregg.presentation.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : BaseViewModel<ProfilePageState>() {

    private val nickNameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val surgeryTypeStateFlow : MutableStateFlow<SurgeryType> = MutableStateFlow(SurgeryType.THINK_SURGERY)
    private val progressRoundStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val startDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val spouseStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: ProfilePageState = ProfilePageState(
        nickName = nickNameStateFlow.asStateFlow(),
        surgeryType = surgeryTypeStateFlow.asStateFlow(),
        progressRound = progressRoundStateFlow.asStateFlow(),
        startDay = startDayStateFlow.asStateFlow(),
        spouse = spouseStateFlow.asStateFlow()
    )

    fun getMyInfo(){
        viewModelScope.launch {
            getMyInfoUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        viewModelScope.launch {
            nickNameStateFlow.update { resourceProvider.getString(R.string.profile_nickname, result.nickName) }
            surgeryTypeStateFlow.update { result.surgeryType }
            progressRoundStateFlow.update { resourceProvider.getString(R.string.account_round_unit, result.round) }
            startDayStateFlow.update { result.startDate }
            spouseStateFlow.update { result.spouse }
        }
    }

    fun onClickEdit(){
        emitEventFlow(ProfileEvent.GoToEditProfileEvent)
    }

    fun onClickMyMedicineInjection(){
        emitEventFlow(ProfileEvent.GoToMyMedicineInjectionEvent)
    }

    fun onClickNotice(){
        emitEventFlow(ProfileEvent.GoToNoticeEvent)
    }

    fun onClickFAQ(){
        emitEventFlow(ProfileEvent.GoToFAQEvent)
    }

    fun onClickAsk(){
        emitEventFlow(ProfileEvent.GoToAskEvent)
    }

    fun onClickPolicy(){
        emitEventFlow(ProfileEvent.GoToPolicyEvent)
    }

    fun onClickAccount(){
        emitEventFlow(ProfileEvent.GoToAccountSettingEvent)
    }
}