package com.example.courses.feature.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.courses.core.presentation.ui.theme.CoursesTheme
import com.example.courses.feature.home.presentation.CourseDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CourseDetailsScreen(
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<CourseDetailsViewModel>()

    CourseDetailsScreen(
        courseId = viewModel.courseId,
        navigateBack = navigateBack,
    )
}

@Composable
fun CourseDetailsScreen(
    courseId: Int,
    navigateBack: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Info about course with id: $courseId")
    }
}

@Preview
@Composable
private fun CourseDetailsScreenPreview() {
    CoursesTheme {
        CourseDetailsScreen(
            courseId = 17,
            navigateBack = {},
        )
    }
}