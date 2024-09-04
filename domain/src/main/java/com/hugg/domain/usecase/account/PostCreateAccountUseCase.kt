package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.account.AccountCreateRequestVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCreateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountCreateRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AccountCreateRequestVo): Flow<ApiState<Unit>> {
        return accountRepository.createAccount(request)
    }
}