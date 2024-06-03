package com.foregg.presentation.ui.main.information

import androidx.lifecycle.ViewModel
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModel<InformationPageState>() {
    private val infoListStateFlow: MutableStateFlow<Map<String, List<InfoItemVo>>> = MutableStateFlow(
        mapOf(
            resourceProvider.getString(R.string.info_possible_subsidy) to listOf(InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시")),
            resourceProvider.getString(R.string.info_essential) to listOf(InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시")),
            resourceProvider.getString(R.string.info_woman_contents) to listOf(InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시")),
            resourceProvider.getString(R.string.info_husband_contents) to listOf(InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"))
        )
    )

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )

    fun onClickBtnSubsidyDetail() {
        emitEventFlow(InformationEvent.GoToSubsidyDetailEvent)
    }

    fun onClickBtnBack() {
        emitEventFlow(InformationEvent.GoBackEvent)
    }
}