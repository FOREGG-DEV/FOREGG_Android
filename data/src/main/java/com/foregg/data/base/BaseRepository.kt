package com.foregg.data.base

import com.foregg.domain.base.ApiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.Reader

abstract class BaseRepository {

     inline fun <reified D, M> apiLaunch(
         crossinline apiCall: suspend () -> Response<ApiResponse<D>>,
         responseMapper: Mapper.ResponseMapper<D, M>,
    ): Flow<ApiState<M>> = flow {

        val response = apiCall()
        when (response.isSuccessful) {
            true -> {
                val apiResponse = response.body() as ApiResponse
                val data = responseMapper.mapDtoToModel(apiResponse.data)
                val apiSuccess = ApiState.Success(data)
                emit(apiSuccess)
            }
            false -> {
                val apiResponse: ApiResponse<String> = fromGson(response.errorBody()?.charStream())
                val code = apiResponse.code.ifEmpty { response.code().toString() }
                val apiError = when(response.code()){
                    404 -> ApiState.Error(apiResponse.data, StatusCode.ERROR_404, apiResponse.message)
                    500 -> ApiState.Error(apiResponse.data, StatusCode.NETWORK_ERROR, apiResponse.message)
                    else -> ApiState.Error(apiResponse.data, code, apiResponse.message)
                }

                emit(apiError)
            }
        }
    }.onStart { emit(ApiState.Loading) }.catch { e: Throwable ->
        e.printStackTrace()
        emit(ApiState.Error(null, StatusCode.ERROR, null))
    }

    inline fun <reified T> fromGson(json: Reader?): ApiResponse<T> {
        return Gson().fromJson(json, object: TypeToken<ApiResponse<T>>() {}.type) ?: ApiResponse()
    }
}