package com.hugg.presentation.module

import com.hugg.data.repository.AccountRepositoryImpl
import com.hugg.data.repository.AuthRepositoryImpl
import com.hugg.data.repository.ChallengeRepositoryImpl
import com.hugg.data.repository.DailyRecordRepositoryImpl
import com.hugg.data.repository.ForeggJwtRepositoryImpl
import com.hugg.data.repository.ProfileRepositoryImpl
import com.hugg.data.repository.ScheduleRepositoryImpl
import com.hugg.domain.repository.AccountRepository
import com.hugg.domain.repository.AuthRepository
import com.hugg.domain.repository.ForeggJwtRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.domain.repository.ScheduleRepository
import com.hugg.data.repository.HomeRepositoryImpl
import com.hugg.data.repository.InformationRepositoryImpl
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.domain.repository.DailyRecordRepository
import com.hugg.domain.repository.HomeRepository
import com.hugg.domain.repository.InformationRepository
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