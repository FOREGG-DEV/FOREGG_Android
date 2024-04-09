package com.foregg.data.base

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.DomainResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.Reader

abstract class BaseRepository {

     inline fun <reified D : DataDto, M : DomainResponse> apiLaunch(
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
                val apiError = ApiState.Error(data, apiResponse.statusCode)
                emit(apiError)
            }
        }
    }.onStart { emit(ApiState.Loading) }.catch { e: Throwable ->
        e.printStackTrace()
        emit(ApiState.Error(null, StatusCode.ERROR))
    }

    inline fun <reified T:DataDto> fromGson(json: Reader?): ApiResponse<T> {
        return Gson().fromJson(json, object: TypeToken<ApiResponse<T>>() {}.type)
    }
}