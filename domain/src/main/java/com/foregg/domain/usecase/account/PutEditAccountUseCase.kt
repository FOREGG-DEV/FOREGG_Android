package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.account.AccountCreateRequestVo
import com.foregg.domain.model.request.account.AccountEditRequestVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountEditRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AccountEditRequestVo): Flow<ApiState<Unit>> {
        return accountRepository.editAccount(request)
    }
}