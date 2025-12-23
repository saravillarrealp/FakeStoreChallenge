package com.svillarreal.fakestorechallenge.ui.product.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductDetailRoute(
    modifier: Modifier = Modifier,
    productId: Int,
    onBack: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.load(productId)
    }

    ProductDetailScreen(
        modifier = modifier,
        uiState = uiState,
        onBack = onBack,
        onToggleFavorite = { id, newValue ->
            viewModel.setFavorite(id, newValue)
        }
    )
}