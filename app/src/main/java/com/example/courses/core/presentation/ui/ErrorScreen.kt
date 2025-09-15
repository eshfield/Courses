package com.example.courses.core.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.courses.R
import com.example.courses.core.presentation.ui.theme.CoursesTheme

@Composable
fun ErrorScreen(
    message: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = message ?: stringResource(R.string.unknown_error)

            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    CoursesTheme {
        ErrorScreen("Что-то пошло не так")
    }
}
