package com.foregg.presentation.ui.main.information

import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor() : BaseViewModel<InformationPageState>() {

    private val infoListStateFlow : MutableStateFlow<List<InfoCategoryListVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )
}