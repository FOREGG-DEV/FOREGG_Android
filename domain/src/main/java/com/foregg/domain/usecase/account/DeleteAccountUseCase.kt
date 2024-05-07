package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Long, ApiState<Unit>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<Unit>> {
        return accountRepository.delete(request)
    }
}