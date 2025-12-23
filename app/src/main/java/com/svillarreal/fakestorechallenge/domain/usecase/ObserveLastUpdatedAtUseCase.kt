package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLastUpdatedAtUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Long?> = repository.observeLastUpdatedAt()
}