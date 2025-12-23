package com.svillarreal.fakestorechallenge.domain.usecase

import com.svillarreal.fakestorechallenge.domain.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveIsOnlineUseCase @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) {
    operator fun invoke(): Flow<Boolean> = connectivityObserver.observeIsOnline()
}