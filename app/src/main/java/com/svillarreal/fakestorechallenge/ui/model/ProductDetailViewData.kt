package com.svillarreal.fakestorechallenge.ui.model

data class ProductDetailViewData (
    val id: Int,
    val title: String,
    val description: String,
    val priceFormatted: String,
    val category: String,
    val imageUrl: String,
    val isFavorite: Boolean
)
