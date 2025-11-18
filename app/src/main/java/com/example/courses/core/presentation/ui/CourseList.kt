package com.example.courses.core.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.courses.R
import com.example.courses.core.presentation.CoursesUiState

@Composable
fun CourseList(
    coursesUiState: CoursesUiState,
    onCourseClick: (Int) -> Unit,
    onAddBookmark: (Int) -> Unit,
    onRemoveBookmark: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier)
    when (coursesUiState) {
        is CoursesUiState.Loading -> LoadingIndicator()
        is CoursesUiState.Success -> {
            val courses = coursesUiState.courses
            if (courses.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = stringResource(R.string.nothing_found),
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = dimensionResource(R.dimen.small),
                        bottom = dimensionResource(R.dimen.medium),
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.medium)
                    ),
                ) {
                    items(courses, key = { it.id }) { course ->
                        CourseCard(
                            course = course,
                            onCourseClick = { onCourseClick(course.id) },
                            onBookmark = {
                                if (course.isBookmarked) {
                                    onRemoveBookmark(course.id)
                                } else {
                                    onAddBookmark(course.id)
                                }
                            },
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }

        is CoursesUiState.Error -> ErrorScreen(message = coursesUiState.message)
    }
}
