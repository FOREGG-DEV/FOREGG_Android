package com.foregg.presentation.ui.main.information

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.domain.usecase.information.GetAllInformationUseCase
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val getAllInformationUseCase: GetAllInformationUseCase
) : BaseViewModel<InformationPageState>() {

    private val infoListStateFlow : MutableStateFlow<List<InfoCategoryListVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )

    fun getInformation(){
        viewModelScope.launch {
            getAllInformationUseCase(Unit).collect{
                resultResponse(it, ::handleSuccessGetInformation)
            }
        }
    }

    private fun handleSuccessGetInformation(result : List<InformationResponseVo>){
        viewModelScope.launch {
            infoListStateFlow.update { getList(result) }
        }
    }

    private fun getList(result : List<InformationResponseVo>) : List<InfoCategoryListVo>{
        return listOf(
            InfoCategoryListVo(
                type = InformationType.ESSENTIAL,
                list = getFilterList(result.filter { it.informationType == InformationType.ESSENTIAL })
            ),
            InfoCategoryListVo(
                type = InformationType.HUGG_PICK,
                list = getFilterList(result.filter { it.informationType == InformationType.HUGG_PICK })
            )
        )
    }

    private fun getFilterList(list: List<InformationResponseVo>): List<InfoItemVo> {
        return list.map {
            InfoItemVo(
                url = it.url,
                tags = it.tag,
                image = it.image
            )
        }
    }
}