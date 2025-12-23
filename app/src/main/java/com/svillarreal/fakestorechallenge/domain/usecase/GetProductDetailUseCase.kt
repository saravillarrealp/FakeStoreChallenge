package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(id: Int): Flow<Product> = repository.getProduct(id)
}