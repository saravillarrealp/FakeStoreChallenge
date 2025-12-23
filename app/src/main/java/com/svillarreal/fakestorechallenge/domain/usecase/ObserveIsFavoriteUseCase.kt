package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(productId: Int): Flow<Boolean> =
        favoriteRepository.observeIsFavorite(productId)
}