package com.example.courses.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.courses.R
import com.example.courses.core.presentation.CoursesUiState
import com.example.courses.core.presentation.model.CourseUiModel
import com.example.courses.core.presentation.ui.CourseList
import com.example.courses.core.presentation.ui.theme.CoursesTheme
import com.example.courses.feature.home.presentation.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val PULL_TO_REFRESH_INDICATOR_TIME_MS = 350L

@Composable
fun HomeScreen(onCourseClick: (Int) -> Unit) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val coursesUiState by viewModel.coursesUiState.collectAsStateWithLifecycle()

    HomeScreen(
        coursesUiState,
        toggleSortByPublishDate = viewModel::toggleSortByPublishDateDesc,
        onCourseClick = onCourseClick,
        onAddBookmark = viewModel::addBookmark,
        onRemoveBookmark = viewModel::removeBookmark,
        onRefresh = viewModel::loadCourses,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    coursesUiState: CoursesUiState,
    toggleSortByPublishDate: () -> Unit,
    onCourseClick: (Int) -> Unit,
    onAddBookmark: (Int) -> Unit,
    onRemoveBookmark: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                onRefresh()
                delay(PULL_TO_REFRESH_INDICATOR_TIME_MS)
                isRefreshing = false
            }
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                query = "",
                onValueChange = {},
                onShowFilters = {},
            )
            SortByButton(
                onClick = toggleSortByPublishDate,
                modifier = Modifier.align(Alignment.End),
            )
            CourseList(
                coursesUiState = coursesUiState,
                onCourseClick = onCourseClick,
                onAddBookmark = onAddBookmark,
                onRemoveBookmark = onRemoveBookmark,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    query: String,
    onValueChange: (String) -> Unit,
    onShowFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            top = dimensionResource(R.dimen.medium),
            bottom = dimensionResource(R.dimen.small),
        ),
    ) {
        TextField(
            value = query,
            onValueChange = onValueChange,
            shape = SearchBarDefaults.inputFieldShape,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.medium)),
                )
            },
            placeholder = { Text(stringResource(R.string.search)) },
            singleLine = true,
            modifier = Modifier.weight(1F)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.small)))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dimensionResource(R.dimen.header_height))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainer),
        ) {
            IconButton(onClick = onShowFilters) {
                Icon(
                    painter = painterResource(R.drawable.filters),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Composable
fun SortByButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(start = 12.dp, end = 0.dp),
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.small_extra)
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.sort_by),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Icon(
                painter = painterResource(R.drawable.arrow_down_up),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    CoursesTheme {
        HomeScreen(
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
            toggleSortByPublishDate = {},
            onCourseClick = {},
            onAddBookmark = {},
            onRemoveBookmark = {},
            onRefresh = {},
        )
    }
}
