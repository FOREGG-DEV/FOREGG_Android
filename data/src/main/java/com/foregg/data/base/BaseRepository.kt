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
                val apiResponse: ApiResponse<D> = fromGson(response.errorBody()?.charStream())
                val data = responseMapper.mapDtoToModel(apiResponse.data)
                val code = apiResponse.code.ifEmpty { response.code().toString() }
                val apiError = when(response.code()){
                    404 -> ApiState.Error(data, StatusCode.ERROR_404)
                    500 -> ApiState.Error(data, StatusCode.NETWORK_ERROR)
                    else -> ApiState.Error(data, code)
                }

                emit(apiError)
            }
        }
    }.onStart { emit(ApiState.Loading) }.catch { e: Throwable ->
        e.printStackTrace()
        emit(ApiState.Error(null, StatusCode.ERROR))
    }

    inline fun <reified T> fromGson(json: Reader?): ApiResponse<T> {
        return Gson().fromJson(json, object: TypeToken<ApiResponse<T>>() {}.type) ?: ApiResponse()
    }
}