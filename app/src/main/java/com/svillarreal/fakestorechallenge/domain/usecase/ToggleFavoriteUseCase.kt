package com.svillarreal.fakestorechallenge.domain.usecase

import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) {
    suspend operator fun invoke(productId: Int, newValue: Boolean) {
        setFavoriteUseCase(productId, newValue)
    }
}