package com.example.courses.feature.bookmarks.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.courses.R
import com.example.courses.core.presentation.CoursesUiState
import com.example.courses.core.presentation.model.CourseUiModel
import com.example.courses.core.presentation.ui.CourseList
import com.example.courses.core.presentation.ui.theme.CoursesTheme
import com.example.courses.feature.bookmarks.presentation.BookmarksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookmarksScreen() {
    val viewModel = koinViewModel<BookmarksViewModel>()
    val coursesUiState by viewModel.coursesUiState.collectAsStateWithLifecycle()

    BookmarksScreen(
        coursesUiState,
        onRemoveBookmark = viewModel::removeBookmark,
    )
}

@Composable
fun BookmarksScreen(
    coursesUiState: CoursesUiState,
    onRemoveBookmark: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            title = stringResource(R.string.bookmarks_title),
            modifier = Modifier.padding(dimensionResource(R.dimen.medium)),
        )
        CourseList(
            coursesUiState = coursesUiState,
            onCourseClick = {},
            onAddBookmark = {},
            onRemoveBookmark = onRemoveBookmark,
        )
    }
}

@Composable
fun Header(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun BookmarksScreenPreview() {
    CoursesTheme {
        BookmarksScreen(
            coursesUiState = CoursesUiState.Success(
                courses = listOf(
                    CourseUiModel(
                        id = 103,
                        title = "Системный аналитик",
                        text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                        price = "12 000",
                        rate = "3.9",
                        startDate = "2024-09-10",
                        imageUrl = "",
                        isBookmarked = true,
                    ),
                ),
            ),
            onRemoveBookmark = {},
        )
    }
}