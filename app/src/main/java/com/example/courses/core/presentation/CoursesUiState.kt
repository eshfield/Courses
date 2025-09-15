package com.example.courses.core.presentation

import com.example.courses.core.presentation.model.CourseUiModel

sealed interface CoursesUiState {
    data object Loading : CoursesUiState
    data class Success(val courses: List<CourseUiModel>) : CoursesUiState
    data class Error(val message: String?) : CoursesUiState
}
