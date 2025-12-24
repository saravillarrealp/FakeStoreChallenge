package com.svillarreal.fakestorechallenge.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.svillarreal.fakestorechallenge.ui.product.detail.ProductDetailRoute
import com.svillarreal.fakestorechallenge.ui.product.list.ProductScreen
import com.svillarreal.fakestorechallenge.ui.product.list.ProductViewModel

fun NavGraphBuilder.productScreenNavigation(
    innerPadding: PaddingValues,
    onProductClick: (Int) -> Unit
) {
    composable(Routes.PRODUCTS) {
        val viewModel = hiltViewModel<ProductViewModel>()
        LaunchedEffect(Unit) {
            viewModel.start()
        }
        ProductScreen(
            modifier = Modifier.padding(innerPadding),
            stateProvider = viewModel,
            onProductClick = onProductClick
        )
    }
}

fun NavGraphBuilder.productDetailScreenNavigation(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    composable(
        route = Routes.PRODUCT_DETAIL,
        arguments =
            listOf(navArgument(Routes.ARG_PRODUCT_ID)
            {type = NavType.IntType})
    ){ backStackEntry ->

        val productId = backStackEntry.arguments
            ?.getInt(Routes.ARG_PRODUCT_ID)
            ?: error("productId missing")

        ProductDetailRoute(
            modifier = Modifier.padding(innerPadding),
            productId = productId,
            onBack = { navController.popBackStack() }
        )
    }
}
