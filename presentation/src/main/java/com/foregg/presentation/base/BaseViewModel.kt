package com.foregg.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foregg.data.base.ApiResponse
import com.foregg.data.base.StatusCode
import com.foregg.domain.base.ApiState
import com.foregg.domain.base.DomainResponse
import com.foregg.presentation.PageState
import com.foregg.presentation.Event
import com.foregg.presentation.util.EventFlow
import com.foregg.presentation.util.MutableEventFlow
import com.foregg.presentation.util.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState> : ViewModel() {

    abstract val uiState:STATE

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow: EventFlow<Event> = _eventFlow.asEventFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    protected fun showLoading(){
        _isLoading.value = true
    }

    private fun endLoading(){
        _isLoading.value = false
    }

    protected fun<D : DomainResponse> resultResponse(response: ApiState<D>, successCallback : (D) -> Unit, errorCallback : ((String) -> Unit)? = null){
        when(response){
            is ApiState.Error -> errorCallback?.invoke(response.errorCode)
            ApiState.Loading -> showLoading()
            is ApiState.Success -> successCallback.invoke(response.data)
        }
        endLoading()
    }
}