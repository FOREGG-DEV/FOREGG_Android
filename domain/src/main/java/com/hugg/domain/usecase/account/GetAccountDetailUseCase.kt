package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.account.AccountDetailResponseVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountDetailUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Long, ApiState<AccountDetailResponseVo>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<AccountDetailResponseVo>> {
        return accountRepository.getAccountDetail(request)
    }
}