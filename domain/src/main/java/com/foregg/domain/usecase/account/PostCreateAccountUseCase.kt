package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.account.AccountCreateEditRequestVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCreateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountCreateEditRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AccountCreateEditRequestVo): Flow<ApiState<Unit>> {
        return accountRepository.createAccount(request)
    }
}