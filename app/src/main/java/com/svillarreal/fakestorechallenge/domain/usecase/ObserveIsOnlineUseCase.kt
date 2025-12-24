package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.repository.connectivity.ConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveIsOnlineUseCase @Inject constructor(
    private val connectivityRepository: ConnectivityRepository
) {
    operator fun invoke(): Flow<Boolean> = connectivityRepository.observeIsOnline()
}