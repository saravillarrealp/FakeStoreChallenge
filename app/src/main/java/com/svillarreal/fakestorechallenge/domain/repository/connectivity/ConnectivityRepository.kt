package com.svillarreal.fakestorechallenge.domain.repository.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    fun observeIsOnline(): Flow<Boolean>
}