package com.svillarreal.fakestorechallenge.data.remote.datasource

import com.svillarreal.fakestorechallenge.data.remote.api.ProductApi
import com.svillarreal.fakestorechallenge.data.remote.model.ProductResponse
import javax.inject.Inject

//TODO: removed if always gonna take the data cached
class ProductRemoteDataSource @Inject constructor(
    private val api: ProductApi
) {
    suspend fun getProducts(limit: Int): List<ProductResponse> {
        return api.getProducts(limit)
    }
}