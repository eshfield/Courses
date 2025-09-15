package com.example.courses.feature.bookmarks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.core.presentation.CoursesUiState
import com.example.courses.core.presentation.model.CourseUiModel
import com.example.courses.feature.bookmarks.data.local.repository.LocalBookmarksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val repository: LocalBookmarksRepository,
) : ViewModel() {
    val coursesUiState = repository.courses
        .map { courses ->
            val uiCourses = courses.map { course ->
                CourseUiModel.fromCourse(
                    course = course,
                    isBookmarked = true,
                )
            }
            CoursesUiState.Success(uiCourses) as CoursesUiState
        }
        .catch { e -> emit(CoursesUiState.Error(e.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CoursesUiState.Loading,
        )

    fun removeBookmark(courseId: Int) {
        viewModelScope.launch {
            repository.removeCourse(courseId)
        }
    }
}
