package com.foregg.presentation.module

import com.foregg.data.repository.AccountRepositoryImpl
import com.foregg.data.repository.AuthRepositoryImpl
import com.foregg.data.repository.ChallengeRepositoryImpl
import com.foregg.data.repository.DailyRecordRepositoryImpl
import com.foregg.data.repository.ForeggJwtRepositoryImpl
import com.foregg.data.repository.ProfileRepositoryImpl
import com.foregg.data.repository.ScheduleRepositoryImpl
import com.foregg.domain.repository.AccountRepository
import com.foregg.domain.repository.AuthRepository
import com.foregg.domain.repository.ForeggJwtRepository
import com.foregg.domain.repository.ProfileRepository
import com.foregg.domain.repository.ScheduleRepository
import com.foregg.data.repository.HomeRepositoryImpl
import com.foregg.data.repository.InformationRepositoryImpl
import com.foregg.domain.repository.ChallengeRepository
import com.foregg.domain.repository.DailyRecordRepository
import com.foregg.domain.repository.HomeRepository
import com.foregg.domain.repository.InformationRepository
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

    @Singleton
    @Binds
    abstract fun providesScheduleRepository(repositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Singleton
    @Binds
    abstract fun providesHomeRepository(repositoryImpl: HomeRepositoryImpl) : HomeRepository

    @Singleton
    @Binds
    abstract fun providesChallengeRepository(repositoryImpl: ChallengeRepositoryImpl) : ChallengeRepository

    @Singleton
    @Binds
    abstract fun providesAccountRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    abstract fun providesProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun providesDailyRecordRepository(repositoryImpl: DailyRecordRepositoryImpl): DailyRecordRepository

    @Singleton
    @Binds
    abstract fun providesInformationRepository(repositoryImpl: InformationRepositoryImpl): InformationRepository
}