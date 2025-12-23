package com.svillarreal.fakestorechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.svillarreal.fakestorechallenge.ui.navigation.Routes
import com.svillarreal.fakestorechallenge.ui.navigation.productDetailScreenNavigation
import com.svillarreal.fakestorechallenge.ui.navigation.productScreenNavigation
import com.svillarreal.fakestorechallenge.ui.theme.FakeStoreChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeStoreChallengeTheme {
                val navController = rememberNavController()

                Scaffold(
                    contentWindowInsets = WindowInsets.systemBars
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Routes.PRODUCTS
                    ) {
                        productScreenNavigation(
                            innerPadding = innerPadding,
                            onProductClick = { productId ->
                                navController.navigate(Routes.productDetail(productId))
                            }
                        )

                        productDetailScreenNavigation(
                            innerPadding = innerPadding,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}