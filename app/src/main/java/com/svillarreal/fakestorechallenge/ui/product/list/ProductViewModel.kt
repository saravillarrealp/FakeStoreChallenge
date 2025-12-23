package com.svillarreal.fakestorechallenge.ui.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svillarreal.fakestorechallenge.data.connectivity.NetworkConnectivityObserver
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveIsOnlineUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveLastUpdatedAtUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.RefreshProductsUseCase
import com.svillarreal.fakestorechallenge.ui.mapper.toViewData
import com.svillarreal.fakestorechallenge.ui.model.ProductViewData
import com.svillarreal.fakestorechallenge.ui.product.list.ProductStateProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val observeProductsUseCase: ObserveProductsUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val observeIsOnlineUseCase: ObserveIsOnlineUseCase,
    private val observeLastUpdatedAtUseCase: ObserveLastUpdatedAtUseCase,
    private val connectivityObserver: NetworkConnectivityObserver
) : ViewModel(), ProductStateProvider {
    private val pageSize = 10
    private val maxFromApi = 20

    private val _allProducts = MutableStateFlow<List<ProductViewData>>(emptyList())
    private val _visibleCount = MutableStateFlow(pageSize)
    private val _isAppending = MutableStateFlow(false)
    private val _isRefreshing = MutableStateFlow(false)
    private val _isOnline = MutableStateFlow(false)

    override val isAppending: StateFlow<Boolean> = _isAppending.asStateFlow()

    val isOnline: StateFlow<Boolean> =
        observeIsOnlineUseCase()
            .catch { emit(false) }
            .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), false)

    val lastUpdatedAt: StateFlow<Long?> =
        observeLastUpdatedAtUseCase()
            .catch { emit(null) }
            .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5_000), null)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    override val uiState: StateFlow<ProductsUiState> =
        combine(
            _allProducts,
            _visibleCount,
            isOnline,
            isRefreshing,
            lastUpdatedAt
        ) { all, visibleCount, online, refreshing, updatedAt ->

            ProductsUiState(
                items = all.take(visibleCount),
                isOnline = online,
                isRefreshing = refreshing,
                lastUpdatedAt = updatedAt
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = ProductsUiState()
        )

    init {
        // observer cache
        getProductsUseCase(limit = maxFromApi)
            .map { it.map { p -> p.toViewData() } }
            .onEach { list ->
                _allProducts.value = list
                _visibleCount.value = minOf(pageSize, list.size)
            }
            .launchIn(viewModelScope)

        //observer connectivity
        connectivityObserver.observeIsOnline()
            .onEach { _isOnline.value = it }
            .launchIn(viewModelScope)

        //initial refresh
        refresh()

        //if is online refresh again
        connectivityObserver.observeIsOnline()
            .distinctUntilChanged()
            .filter { it }
            .onEach { refresh() }
            .launchIn(viewModelScope)

    }

    private fun observeProductsFromCache() {
        observeProductsUseCase(limit = maxFromApi)
            .map { list -> list.map { it.toViewData() } }
            .onEach { viewData ->
                _allProducts.value = viewData
                _visibleCount.value = minOf(_visibleCount.value, maxOf(pageSize, viewData.size))
                // si cache quedó más chico, ajusta visibleCount para no pasarte
                _visibleCount.value = minOf(_visibleCount.value, viewData.size.coerceAtLeast(0))
                if (_visibleCount.value == 0) _visibleCount.value = minOf(pageSize, viewData.size)
            }
            .catch { e ->
                Timber.Forest.e(e, "Error observing cached products")
                _allProducts.value = emptyList()
                _visibleCount.value = pageSize
            }
            .launchIn(viewModelScope)
    }

    private fun autoRefreshWhenOnline() {
        isOnline
            .onEach { online ->
                if (online) refresh()
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            if (_isRefreshing.value) return@launch
            _isRefreshing.value = true
            Timber.d("Refreshing products...")

            runCatching { refreshProductsUseCase(limit = maxFromApi) }
                .onSuccess { Timber.d("Refresh SUCCESS") }
                .onFailure { Timber.e(it, "Refresh FAILED") }

            _isRefreshing.value = false
        }
    }

    override fun loadNextPage() {
        val total = _allProducts.value.size
        val current = _visibleCount.value
        if (current >= total) return
        if (_isAppending.value) return

        viewModelScope.launch {
            _isAppending.value = true
            delay(600)
            _visibleCount.value = minOf(current + pageSize, total)
            _isAppending.value = false
        }
    }
}