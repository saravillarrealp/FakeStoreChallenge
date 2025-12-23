package com.svillarreal.fakestorechallenge.ui.product.detail

import app.cash.turbine.test
import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.domain.usecase.GetProductDetailUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.ObserveIsFavoriteUseCase
import com.svillarreal.fakestorechallenge.domain.usecase.SetFavoriteUseCase
import com.svillarreal.fakestorechallenge.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductDetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(dispatcher)

    private val getProductDetailUseCase: GetProductDetailUseCase = mockk()

    private val getProductUseCase: GetProductDetailUseCase = mockk()
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase = mockk()
    private val setFavoriteUseCase: SetFavoriteUseCase = mockk(relaxed = true)



    /**
     * NOTE:
     * The structure and list of test cases in this test class below were initially generated
     * with the assistance of Gemini (AI).
     *
     * The implementation, adaptations, and assertions were reviewed and adjusted
     * manually to fit the project architecture, business logic, and testing strategy by
     * Sara Villarreal
     */


    @Test
    fun `uiState initial state check`() {
        // Verify that the initial state of uiState is ProductDetailUiState.Loading before any ID is provided.
        // Given
        every { getProductDetailUseCase.invoke(any()) } returns flow { error("should not be called") }
        every { observeIsFavoriteUseCase.invoke(any()) } returns flow { error("should not be called") }

        val vm = ProductDetailViewModel(
            getProductDetailUseCase = getProductDetailUseCase,
            observeIsFavoriteUseCase = observeIsFavoriteUseCase,
            setFavoriteUseCase = setFavoriteUseCase
        )

        // Then
        assertTrue(vm.uiState.value is ProductDetailUiState.Loading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load emits loading state on new ID`() = runTest {
        // Given
        every { getProductDetailUseCase(any()) } returns flowOf(fakeProduct(3))
        every { observeIsFavoriteUseCase(any()) } returns flowOf(false)

        val viewModel = ProductDetailViewModel(
            getProductDetailUseCase,
            observeIsFavoriteUseCase,
            setFavoriteUseCase
        )

        viewModel.uiState.test {
            // WHEN
            viewModel.load(1)

            // THEN
            val loading = awaitItem()
            assertTrue(loading is ProductDetailUiState.Loading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `load deduplication with same ID`() {
        // Ensure that calling load() with the same productId as the current value does not re-trigger the flow or emit new states.
        // TODO implement test
    }

    @Test
    fun `uiState success on valid data emission`() {
        // Test that uiState emits Success with correctly mapped ProductDetailViewData when use cases emit valid data.
        // TODO implement test
    }

    @Test
    fun `uiState error handling on GetProductDetailUseCase failure`() {
        // Verify that uiState emits ProductDetailUiState.Error with the exception message when GetProductDetailUseCase throws an error.
        // TODO implement test
    }

    @Test
    fun `uiState error handling on ObserveIsFavoriteUseCase failure`() {
        // Verify that uiState emits ProductDetailUiState.Error when ObserveIsFavoriteUseCase fails, ensuring the catch operator works on the combined flow.
        // TODO implement test
    }

    @Test
    fun `uiState fallback to default error message`() {
        // Test if uiState emits 'Unknown error' when an exception with a null message is caught in the flow.
        // TODO implement test
    }

    @Test
    fun `uiState reactivity to favorite status updates`() {
        // Verify that uiState emits a new Success state automatically when ObserveIsFavoriteUseCase emits a new value without calling load again.
        // TODO implement test
    }

    @Test
    fun `uiState reactivity to product detail updates`() {
        // Verify that uiState updates when GetProductDetailUseCase emits updated product information for the same ID.
        // TODO implement test
    }

    @Test
    fun `load handles rapid sequential calls`() {
        // Test if flatMapLatest cancels previous collection and only processes the latest productId when load() is called rapidly.
        // TODO implement test
    }

    @Test
    fun `setFavorite invokes use case with correct parameters`() {
        // Verify that setFavorite(id, value) correctly calls setFavoriteUseCase with the exact productId and boolean values provided.
        // TODO implement test
    }

    @Test
    fun `setFavorite execution in viewModelScope`() {
        // Ensure setFavorite initiates the use case execution and persists even if the immediate caller's scope is restricted, utilizing viewModelScope.
        // TODO implement test
    }

    @Test
    fun `uiState WhileSubscribed timeout behavior`() {
        // Test if the StateFlow remains active for 5 seconds after unsubscription and stops collection thereafter to prevent memory leaks.
        // TODO implement test
    }

    @Test
    fun `uiState filterNotNull edge case`() {
        // Verify that the flow remains inactive and does not emit beyond the initial value if load() is never called (productId remains null).
        // TODO implement test
    }

    @Test
    fun `Timber logging on flow error`() {
        // Check if Timber.e is invoked with the correct exception and log message when the flow encounters an error.
        // TODO implement test
    }


    private fun fakeProduct(id: Int) = Product(
        id = id,
        title = "Productico $id",
        price = 10.0,
        description = "Es una descripcion $id",
        category = "joyas",
        image = "https://chanllenge.com/$id.png"
    )

}