package com.example.courses.feature.home.data.remote.model

import com.example.courses.core.domain.model.Course
import kotlinx.serialization.Serializable

@Serializable
data class CoursesDto(
    val courses: List<Course>,
)
