package com.svillarreal.fakestorechallenge.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observeIsOnline(): Flow<Boolean>
}