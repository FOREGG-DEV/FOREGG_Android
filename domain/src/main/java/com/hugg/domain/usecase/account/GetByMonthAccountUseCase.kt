package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByMonthAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<String, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByMonth(request)
    }
}