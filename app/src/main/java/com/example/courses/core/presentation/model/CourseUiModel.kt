package com.example.courses.core.presentation.model

import com.example.courses.core.domain.model.Course

data class CourseUiModel(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val imageUrl: String,
    val isBookmarked: Boolean,
) {
    companion object {
        fun fromCourse(course: Course, isBookmarked: Boolean) =
            CourseUiModel(
                id = course.id,
                title = course.title,
                text = course.text,
                price = course.price,
                rate = course.rate,
                startDate = course.startDate,
                imageUrl = course.imageUrl,
                isBookmarked = isBookmarked,
            )
    }
}
