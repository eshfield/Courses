package com.example.courses.feature.bookmarks.domain.repository

import com.example.courses.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    val courses: Flow<List<Course>>

    suspend fun addCourse(course: Course)

    suspend fun removeCourse(courseId: Int)
}
