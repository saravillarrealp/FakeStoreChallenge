package com.svillarreal.fakestorechallenge.ui.product.list

import app.cash.turbine.test
import com.svillarreal.fakestorechallenge.data.connectivity.NetworkConnectivityObserver
import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.domain.repository.ProductRepository
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveIsOnlineUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveLastUpdatedAtUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.RefreshProductsUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.SetFavoriteUseCase
import com.svillarreal.fakestorechallenge.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {


    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val observeProductsUseCase: ObserveProductsUseCase = mockk()
    private val refreshProductsUseCase: RefreshProductsUseCase = mockk(relaxed = true)
    private val observeIsOnlineUseCase: ObserveIsOnlineUseCase = mockk()
    private val observeLastUpdatedAtUseCase: ObserveLastUpdatedAtUseCase = mockk()
    private val connectivityObserver: NetworkConnectivityObserver = mockk()

    @Test
    fun `uiState updates isOnline when observeIsOnline emits`() = runTest {
        // Given — Stub init calls
        every { getProductsUseCase.invoke(20) } returns flowOf(emptyList())
        every { observeProductsUseCase.invoke(20) } returns flowOf(emptyList())
        every { observeIsOnlineUseCase.invoke() } returns flowOf(true)
        every { observeLastUpdatedAtUseCase.invoke() } returns flowOf(null)
        every { connectivityObserver.observeIsOnline() } returns flowOf(true)
        coEvery { refreshProductsUseCase.invoke(20) } returns Unit

        val vm = ProductViewModel(
            getProductsUseCase = getProductsUseCase,
            refreshProductsUseCase = refreshProductsUseCase,
            observeIsOnlineUseCase = observeIsOnlineUseCase,
            observeLastUpdatedAtUseCase = observeLastUpdatedAtUseCase,
            connectivityObserver = connectivityObserver
        )

        // Then
        vm.uiState.test {
            val state = awaitItem()
            assertTrue(state.isOnline)
            cancelAndIgnoreRemainingEvents()
        }
    }
}