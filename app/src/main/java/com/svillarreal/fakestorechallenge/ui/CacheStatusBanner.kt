package com.svillarreal.fakestorechallenge.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.concurrent.TimeUnit

@Composable
fun CacheStatusBanner(
    isOnline: Boolean,
    isRefreshing: Boolean,
    lastUpdatedAt: Long?,
    modifier: Modifier = Modifier
) {
    val status = when {
        !isOnline -> "Offline — showing saved data "
        isRefreshing -> "Online — refreshing (showing cache)"
        else -> "Online — showing cache"
    }

    val lastUpdated = lastUpdatedAt?.let { ts ->
        val mins = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - ts)
        " · updated ${mins} min ago"
    }.orEmpty()

    Text(
        text = status + lastUpdated,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}