package com.hugg.presentation.ui.main.profile.ask

import com.hugg.presentation.PageState
import com.hugg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AskViewModel @Inject constructor() : BaseViewModel<PageState.Default>() {

    fun onClickBack(){
        emitEventFlow(AskEvent.GoToBackEvent)
    }

    fun onClickCopy(){
        emitEventFlow(AskEvent.OnClickCopyEmailEvent)
    }

    override val uiState: PageState.Default
        get() = TODO("Not yet implemented")
}