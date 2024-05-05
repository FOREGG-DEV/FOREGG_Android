package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.AccountResponseVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByMonthAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<String, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByMonth(request)
    }
}