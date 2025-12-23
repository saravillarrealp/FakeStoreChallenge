package com.svillarreal.fakestorechallenge.data.repository

import com.svillarreal.fakestorechallenge.data.local.dao.ProductDao
import com.svillarreal.fakestorechallenge.data.mapper.toDomain
import com.svillarreal.fakestorechallenge.data.mapper.toEntity
import com.svillarreal.fakestorechallenge.data.remote.api.ProductApi
import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val dao: ProductDao
) : ProductRepository {

    override fun observeProducts(limit: Int): Flow<List<Product>> =
        dao.observeProducts()
            .map { list -> list.take(limit).map { it.toDomain() } }

    override fun observeLastUpdatedAt(): Flow<Long?> =
        dao.observeLastUpdatedAt()

    override suspend fun refreshProducts(limit: Int) {
        val remote = api.getProducts(limit)
        val now = System.currentTimeMillis()
        dao.upsertAll(remote.map { it.toEntity(updatedAt = now) })
    }

    override fun getProduct(productId: Int): Flow<Product> =
        dao.observeProducts()
            .map { list -> list.first { it.id == productId }.toDomain() }
}