package com.svillarreal.fakestorechallenge.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeIsFavorite(productId: Int): Flow<Boolean>
    suspend fun setFavorite(productId: Int, isFavorite: Boolean)
}