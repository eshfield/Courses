package com.example.courses.core.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    background = backgroundDark,
    outlineVariant = outlineVariantDark,
    surfaceContainer = surfaceContainerDark,
    secondaryContainer = secondaryContainerDark,
)

@Composable
fun CoursesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
