package com.hugg.domain.usecase.jwtToken

import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFroeggRefreshTokenUseCase @Inject constructor(
    private val foreggJwtRepository: ForeggJwtRepository
): UseCase<Unit, String>() {
    override suspend operator fun invoke(request: Unit): Flow<String> {
        return foreggJwtRepository.getRefreshToken()
    }
}