package com.foregg.presentation.ui.main.profile.ask

import com.foregg.presentation.PageState
import com.foregg.presentation.base.BaseViewModel
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