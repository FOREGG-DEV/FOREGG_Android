package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.account.AccountEditRequestVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountEditRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AccountEditRequestVo): Flow<ApiState<Unit>> {
        return accountRepository.editAccount(request)
    }
}