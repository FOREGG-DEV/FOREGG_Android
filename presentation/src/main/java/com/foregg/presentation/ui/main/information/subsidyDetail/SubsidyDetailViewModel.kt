package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.lifecycle.ViewModel
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SubsidyDetailViewModel @Inject constructor() : BaseViewModel<SubsidyDetailPageState>() {
    private val subsidyListStateFlow: MutableStateFlow<List<InfoItemVo>> = MutableStateFlow(listOf(
        InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시"), InfoItemVo("서울시")
    ))

    override val uiState: SubsidyDetailPageState = SubsidyDetailPageState(
        subsidyList = subsidyListStateFlow.asStateFlow()
    )
}