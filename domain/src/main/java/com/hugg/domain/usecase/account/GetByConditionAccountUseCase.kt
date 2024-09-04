package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByConditionAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountGetConditionRequestVo, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: AccountGetConditionRequestVo): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByCondition(request)
    }
}