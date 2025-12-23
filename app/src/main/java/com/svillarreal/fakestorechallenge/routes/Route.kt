package com.svillarreal.fakestorechallenge.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Splash : Route
    @Serializable
    data class Product(val productId: String) : Route
    @Serializable
    data class ProductDetail(val productId: Int) : Route
}