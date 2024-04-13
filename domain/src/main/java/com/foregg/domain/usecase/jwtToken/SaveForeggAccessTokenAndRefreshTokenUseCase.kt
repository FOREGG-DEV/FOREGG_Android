package com.foregg.domain.usecase.jwtToken

import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.SaveForeggJwtRequestVo
import com.foregg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveForeggAccessTokenAndRefreshTokenUseCase @Inject constructor(
    private val plubJwtRepository: ForeggJwtRepository
): UseCase<SaveForeggJwtRequestVo, Boolean>() {
    override suspend operator fun invoke(request: SaveForeggJwtRequestVo): Flow<Boolean> {
        return plubJwtRepository.saveAccessTokenAndRefreshToken(request)
    }
}