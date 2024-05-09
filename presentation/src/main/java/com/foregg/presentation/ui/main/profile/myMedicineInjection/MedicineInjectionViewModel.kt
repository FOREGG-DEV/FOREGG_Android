package com.foregg.presentation.ui.main.profile.myMedicineInjection

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.ProfileMedicineInjectionType
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.domain.usecase.profile.GetMyInfoUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineInjectionViewModel @Inject constructor(
) : BaseViewModel<MedicineInjectionPageState>() {

    private val tabTypeStateFlow : MutableStateFlow<ProfileMedicineInjectionType> = MutableStateFlow(ProfileMedicineInjectionType.MEDICINE)
    private val itemListStateFlow : MutableStateFlow<List<MyMedicineInjectionResponseVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: MedicineInjectionPageState = MedicineInjectionPageState(
        tabType = tabTypeStateFlow.asStateFlow(),
        itemList = itemListStateFlow.asStateFlow()
    )

    fun getMyMedicineInjection(){

    }

    fun onClickTab(type : ProfileMedicineInjectionType){
        viewModelScope.launch {
            tabTypeStateFlow.update { type}
        }
    }

    fun onClickBack(){
        emitEventFlow(MedicineInjectionEvent.GoToBackEvent)
    }
}