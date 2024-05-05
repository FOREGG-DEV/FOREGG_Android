package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.AccountGetConditionRequestVo
import com.foregg.domain.model.response.AccountResponseVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByCountAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Int, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: Int): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByCount(request)
    }
}