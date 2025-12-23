package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import javax.inject.Inject

class RefreshProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(limit: Int) = repository.refreshProducts(limit)
}