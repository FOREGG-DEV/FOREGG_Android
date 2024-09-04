package com.hugg.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugg.data.base.StatusCode
import com.hugg.domain.base.ApiState
import com.hugg.presentation.PageState
import com.hugg.presentation.Event
import com.hugg.presentation.util.EventFlow
import com.hugg.presentation.util.ForeggAnalytics
import com.hugg.presentation.util.MutableEventFlow
import com.hugg.presentation.util.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState> : ViewModel() {

    abstract val uiState:STATE

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event> = _eventFlow.asEventFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _commonError = MutableLiveData<String>()
    val commonError: LiveData<String> = _commonError

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun showLoading(){
        _isLoading.value = true
    }

    private fun endLoading(){
        _isLoading.value = false
    }

    protected fun<D> resultResponse(response: ApiState<D>, successCallback : (D) -> Unit, errorCallback : ((String) -> Unit)? = null, needLoading : Boolean = false){
        when(response){
            is ApiState.Error -> {
                if(response.errorCode == StatusCode.ERROR_404 ||
                    response.errorCode == StatusCode.ERROR ||
                    response.errorCode == StatusCode.NETWORK_ERROR) {
                    ForeggAnalytics.logEvent("error_${response.errorCode}_${response.data}_${response.message}", "apiScreen")
                    _commonError.value = response.errorCode
                }
                else errorCallback?.invoke(response.errorCode)
                endLoading()
            }
            ApiState.Loading -> if(needLoading) showLoading()
            is ApiState.Success -> {
                successCallback.invoke(response.data)
                endLoading()
            }
        }
    }
}