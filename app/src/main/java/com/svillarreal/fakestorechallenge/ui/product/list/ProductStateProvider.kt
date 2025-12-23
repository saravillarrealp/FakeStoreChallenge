package com.svillarreal.fakestorechallenge.ui.product.list

import kotlinx.coroutines.flow.StateFlow

interface ProductStateProvider {
    val uiState: StateFlow<ProductsUiState>
    val isAppending: StateFlow<Boolean>
    fun loadNextPage()
}