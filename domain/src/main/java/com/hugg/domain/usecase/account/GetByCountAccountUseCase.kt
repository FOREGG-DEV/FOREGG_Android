package com.hugg.domain.usecase.account

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByCountAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): UseCase<Int, ApiState<AccountResponseVo>>() {
    override suspend operator fun invoke(request: Int): Flow<ApiState<AccountResponseVo>> {
        return accountRepository.getByCount(request)
    }
}