package com.example.courses.feature.home.presentation.data.remote.repository

import com.example.courses.feature.home.presentation.data.remote.datasource.ApiService
import com.example.courses.core.domain.model.Course
import com.example.courses.feature.home.presentation.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCoursesRepository(
    private val apiService: ApiService,
) : CoursesRepository {
    override suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        val coursesDto = apiService.getCourses()
        coursesDto.courses
    }
}
