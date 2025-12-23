package com.svillarreal.fakestorechallenge.ui.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductDetailUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveIsFavoriteUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.SetFavoriteUseCase
import com.svillarreal.fakestorechallenge.ui.mapper.toDetailViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel() {

    private val _productId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProductDetailUiState> =
        _productId
            .filterNotNull()
            .flatMapLatest { id ->
                combine(
                    getProductDetailUseCase(id),
                    observeIsFavoriteUseCase(id)
                ) { product, isFav ->
                    ProductDetailUiState.Success(product.toDetailViewData(isFav)) as ProductDetailUiState
                }
                    .onStart { emit(ProductDetailUiState.Loading) }
                    .catch { e ->
                        Timber.e(e, "Error loading product detail")
                        emit(ProductDetailUiState.Error(e.message ?: "Unknown error"))
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProductDetailUiState.Loading
            )

    fun load(productId: Int) {
        if (_productId.value == productId) return
        _productId.value = productId
    }

    fun setFavorite(productId: Int, newValue: Boolean) {
        viewModelScope.launch {
            setFavoriteUseCase(productId, newValue)
        }
    }
}