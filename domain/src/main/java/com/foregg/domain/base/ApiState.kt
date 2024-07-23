package com.foregg.domain.base

sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Success<T>(val data : T) : ApiState<T>()
    data class Error(val data : String?, val errorCode : String, val message : String?) : ApiState<Nothing>()
}