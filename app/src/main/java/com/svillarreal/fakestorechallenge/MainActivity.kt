package com.svillarreal.fakestorechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.svillarreal.fakestorechallenge.ui.navigation.Routes
import com.svillarreal.fakestorechallenge.ui.navigation.productDetailScreenNavigation
import com.svillarreal.fakestorechallenge.ui.navigation.productScreenNavigation
import com.svillarreal.fakestorechallenge.ui.product.detail.ProductDetailRoute
import com.svillarreal.fakestorechallenge.ui.product.list.ProductScreen
import com.svillarreal.fakestorechallenge.ui.product.list.ProductViewModel
import com.svillarreal.fakestorechallenge.ui.theme.FakeStoreChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FakeStoreChallengeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.PRODUCTS
                ) {
                    productScreenNavigation(
                        innerPadding = PaddingValues(),
                        onProductClick = { productId ->
                            navController.navigate(Routes.productDetail(productId))
                        }
                    )

                    productDetailScreenNavigation(
                        innerPadding = PaddingValues(),
                        navController = navController
                    )

                }

            }
        }
    }
}