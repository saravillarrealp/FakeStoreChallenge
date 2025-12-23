package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.repository.FavoriteRepository
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(productId: Int, isFavorite: Boolean) {
        favoriteRepository.setFavorite(productId, isFavorite)
    }
}