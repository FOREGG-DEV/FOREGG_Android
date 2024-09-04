package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Long, ApiState<Unit>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<Unit>> {
        return accountRepository.delete(request)
    }
}