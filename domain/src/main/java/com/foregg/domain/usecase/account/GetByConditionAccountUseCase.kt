package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.AccountGetConditionRequestVo
import com.foregg.domain.model.response.AccountResponseVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByConditionAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<AccountGetConditionRequestVo, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: AccountGetConditionRequestVo): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByCondition(request)
    }
}