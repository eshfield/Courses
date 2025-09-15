package com.example.courses.feature.home.presentation.domain.repository

import com.example.courses.core.domain.model.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
}
