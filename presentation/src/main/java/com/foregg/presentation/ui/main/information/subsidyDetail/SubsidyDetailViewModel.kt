package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.domain.usecase.information.GetAllInformationByTypeUseCase
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubsidyDetailViewModel @Inject constructor(
    private val getAllInformationByTypeUseCase: GetAllInformationByTypeUseCase
) : BaseViewModel<SubsidyDetailPageState>() {

    private val subsidyListStateFlow: MutableStateFlow<List<InfoItemVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: SubsidyDetailPageState = SubsidyDetailPageState(
        subsidyList = subsidyListStateFlow.asStateFlow()
    )

    fun getDetailList(type : InformationType){
        when(type){
            InformationType.ESSENTIAL -> getInformationListByType(InformationType.ESSENTIAL)
            InformationType.HUGG_PICK -> getInformationListByType(InformationType.HUGG_PICK)
            InformationType.NOTHING -> {}
        }
    }

    private fun getInformationListByType(type: InformationType){
        viewModelScope.launch {
            getAllInformationByTypeUseCase(type).collect{
                resultResponse(it, ::handleSuccessGetInformationList)
            }
        }
    }

    private fun handleSuccessGetInformationList(result : List<InformationResponseVo>){
        viewModelScope.launch {
            subsidyListStateFlow.update { getItemList(result) }
        }
    }

    private fun getItemList(list : List<InformationResponseVo>) : List<InfoItemVo>{
        return list.map {
            InfoItemVo(
                url = it.url,
                tags = it.tag,
                image = it.image
            )
        }
    }

    fun onClickBack(){
        emitEventFlow(SubsidyDetailEvent.OnClickBack)
    }
}