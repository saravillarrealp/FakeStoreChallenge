package com.svillarreal.fakestorechallenge.ui.product.detail

import com.svillarreal.fakestorechallenge.ui.model.ProductDetailViewData

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(val product: ProductDetailViewData) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}