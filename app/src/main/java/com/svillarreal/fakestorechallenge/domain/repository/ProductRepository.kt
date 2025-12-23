package com.svillarreal.fakestorechallenge.domain.repository

import com.svillarreal.fakestorechallenge.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun observeProducts(limit: Int): Flow<List<Product>>
    fun observeLastUpdatedAt(): Flow<Long?>
    suspend fun refreshProducts(limit: Int)
    fun getProduct(productId: Int): Flow<Product>
}