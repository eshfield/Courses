package com.example.courses.core.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.courses.R
import kotlinx.coroutines.delay

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    delayMillis: Long = 500L,
) {
    var showIndicator by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMillis)
        showIndicator = true
    }

    if (showIndicator) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(
                    dimensionResource(R.dimen.loading_indicator_size)
                ),
            )
        }
    }
}
