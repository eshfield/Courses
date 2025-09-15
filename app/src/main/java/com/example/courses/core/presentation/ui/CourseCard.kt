package com.example.courses.core.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.courses.R
import com.example.courses.core.presentation.model.CourseUiModel
import com.example.courses.core.presentation.ui.theme.CoursesTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun CourseCard(
    course: CourseUiModel,
    onCourseClick: () -> Unit,
    onBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hazeState = rememberHazeState()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCourseClick),
    ) {
        Box(
            modifier = Modifier.height(
                dimensionResource(R.dimen.course_card_image_height)
            ),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(course.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeSource(state = hazeState),
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.small)),
            ) {
                BookmarkIcon(
                    bookmarked = course.isBookmarked,
                    onClick = onBookmark,
                    hazeState = hazeState,
                    modifier = Modifier.align(Alignment.End),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.small_extra)
                    ),
                ) {
                    GlassPill(hazeState) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(R.dimen.small_extra)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.star_fill),
                                contentDescription = null,
                                tint = Color.Unspecified,
                            )
                            Text(
                                text = course.rate,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    GlassPill(hazeState) {
                        Text(
                            text = course.startDate,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium)),
        ) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = course.text,
                style = MaterialTheme.typography.bodySmall,
                color = LocalContentColor.current.copy(alpha = 0.7F),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${course.price} ₽",
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.course_more),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Icon(
                        painter = painterResource(R.drawable.arrow_right_short_fill),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkIcon(
    bookmarked: Boolean,
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(dimensionResource(R.dimen.bookmark_size))
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                        alpha = 0.3F,
                    ),
                    blurRadius = 16.dp,
                    tints = emptyList(),
                ),
            ),
    ) {
        val res = if (bookmarked) R.drawable.bookmark_fill else R.drawable.bookmark
        Icon(
            painter = painterResource(res),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Composable
fun GlassPill(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.medium_small)))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                        alpha = 0.3F,
                    ),
                    blurRadius = 16.dp,
                    tints = emptyList(),
                ),
            ),
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.glass_pill_horizontal),
                vertical = dimensionResource(R.dimen.glass_pill_vertical),
            )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun CourseCardPreview() {
    CoursesTheme {
        CourseCard(
            course = CourseUiModel(
                id = 103,
                title = "Системный аналитик",
                text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                price = "12 000",
                rate = "3.9",
                startDate = "2024-09-10",
                imageUrl = "",
                isBookmarked = true,
            ),
            onCourseClick = {},
            onBookmark = {},
        )
    }
}
