package com.svillarreal.fakestorechallenge.data.repository

import com.svillarreal.fakestorechallenge.data.local.entity.FavoriteEntity
import com.svillarreal.fakestorechallenge.data.local.dao.FavoriteDao
import com.svillarreal.fakestorechallenge.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {
    override fun observeFavoriteIds(): Flow<Set<Int>> =
        dao.observeFavoriteIds()
            .map { it.toSet() }
            .distinctUntilChanged()

    override fun observeIsFavorite(productId: Int): Flow<Boolean> =
        dao.observeIsFavoriteCount(productId)
            .map { count -> count > 0 }
            .distinctUntilChanged()

    override suspend fun setFavorite(productId: Int, isFavorite: Boolean) {
        if (isFavorite) dao.addFavorite(FavoriteEntity(productId))
        else dao.removeFavorite(productId)
    }
}