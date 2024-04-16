package com.foregg.presentation.module

import com.foregg.data.api.AuthApi
import com.foregg.data.api.ForeggJwtTokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providePlubJwtTokenApi(@NormalRetrofit retrofit: Retrofit): ForeggJwtTokenApi {
        return retrofit.create(ForeggJwtTokenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthTokenApi(@NormalRetrofit retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}