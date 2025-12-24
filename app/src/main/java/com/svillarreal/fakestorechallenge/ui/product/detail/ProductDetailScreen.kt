package com.svillarreal.fakestorechallenge.ui.product.detail
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState,
    onBack: () -> Unit,
    onToggleFavorite: (productId: Int, newValue: Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val product = (uiState as? ProductDetailUiState.Success)?.product
                    if (product != null) {
                        IconButton(onClick = {
                            onToggleFavorite(uiState.product.id,
                                !uiState.product.isFavorite) }) {
                            Icon(
                                imageVector = if (uiState.product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                ProductDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ProductDetailUiState.Error -> {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is ProductDetailUiState.Success -> {
                    val p = uiState.product

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = p.imageUrl,
                            contentDescription = p.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(Modifier.height(16.dp))

                        //Title
                        Text(
                            text = p.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(8.dp))

                        //Category
                        AssistChip(
                            onClick = { },
                            label = { Text(p.category) }
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = p.priceFormatted,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = p.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(Modifier.height(24.dp))

                        Button(
                            onClick = { onToggleFavorite(p.id, !p.isFavorite) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (p.isFavorite) "Remove from favorites" else "Add to favorites")
                        }
                    }
                }
            }
        }
    }
}