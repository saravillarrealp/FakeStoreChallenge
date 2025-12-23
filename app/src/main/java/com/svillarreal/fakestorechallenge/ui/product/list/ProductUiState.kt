package com.svillarreal.fakestorechallenge.ui.product.list

import com.svillarreal.fakestorechallenge.ui.model.ProductViewData

data class ProductsUiState(
    val items: List<ProductViewData> = emptyList(),
    val isOnline: Boolean = false,
    val isRefreshing: Boolean = false,
    val lastUpdatedAt: Long? = null
)