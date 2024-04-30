package com.foregg.presentation.module

import com.foregg.data.repository.AuthRepositoryImpl
import com.foregg.data.repository.ForeggJwtRepositoryImpl
import com.foregg.domain.repository.AuthRepository
import com.foregg.domain.repository.ForeggJwtRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun providesForeggJwtRepository(repositoryImpl: ForeggJwtRepositoryImpl): ForeggJwtRepository
}