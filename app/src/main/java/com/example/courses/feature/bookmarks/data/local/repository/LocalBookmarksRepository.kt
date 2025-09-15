package com.example.courses.feature.bookmarks.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.courses.core.domain.model.Course
import com.example.courses.feature.bookmarks.data.local.datasource.BookmarksSerializer
import com.example.courses.feature.bookmarks.domain.repository.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalBookmarksRepository(
    private val dataStore: DataStore<Preferences>,
) : BookmarksRepository {
    private val serializer = BookmarksSerializer(
        keyName = "bookmarks",
        serializer = Course.serializer(),
    )

    override val courses: Flow<List<Course>>
        get() = dataStore.data.map { prefs ->
            serializer.deserialize(prefs)
        }

    override suspend fun addCourse(course: Course) {
        dataStore.edit { prefs ->
            val courses = serializer.deserialize(prefs)
            val updatedCourses = courses + course
            prefs[serializer.key] = serializer.serialize(updatedCourses)
        }
    }

    override suspend fun removeCourse(courseId: Int) {
        dataStore.edit { prefs ->
            val courses = serializer.deserialize(prefs)
            val updatedCourses = courses.filter { it.id != courseId }
            prefs[serializer.key] = serializer.serialize(updatedCourses)
        }
    }
}
