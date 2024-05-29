package com.foregg.presentation.ui.main.information

import androidx.lifecycle.ViewModel
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InformationViewModel : BaseViewModel<InformationPageState>() {
    private val infoListStateFlow: MutableStateFlow<List<InfoItemVo>> = MutableStateFlow(listOf(
        InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시")
    ))

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )

    fun onClickBtnSubsidyDetail() {
        emitEventFlow(InformationEvent.GoToSubsidyDetailEvent)
    }
}