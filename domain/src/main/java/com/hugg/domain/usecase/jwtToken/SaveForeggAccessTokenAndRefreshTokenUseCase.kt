package com.hugg.domain.usecase.jwtToken

import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveForeggAccessTokenAndRefreshTokenUseCase @Inject constructor(
    private val plubJwtRepository: ForeggJwtRepository
): UseCase<SaveForeggJwtRequestVo, Boolean>() {
    override suspend operator fun invoke(request: SaveForeggJwtRequestVo): Flow<Boolean> {
        return plubJwtRepository.saveAccessTokenAndRefreshToken(request)
    }
}