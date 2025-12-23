package com.svillarreal.fakestorechallenge.data.remote.api

import com.svillarreal.fakestorechallenge.data.remote.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10
    ): List<ProductResponse>
}