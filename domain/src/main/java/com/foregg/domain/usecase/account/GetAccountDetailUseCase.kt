package com.foregg.domain.usecase.account

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.account.AccountDetailResponseVo
import com.foregg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountDetailUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Long, ApiState<AccountDetailResponseVo>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<AccountDetailResponseVo>> {
        return accountRepository.getAccountDetail(request)
    }
}