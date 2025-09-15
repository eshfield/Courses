package com.example.courses.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.core.presentation.CoursesUiState
import com.example.courses.core.presentation.model.CourseUiModel
import com.example.courses.feature.bookmarks.data.local.repository.LocalBookmarksRepository
import com.example.courses.feature.home.presentation.data.remote.repository.RemoteCoursesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel(
    private val coursesRepository: RemoteCoursesRepository,
    private val bookmarksRepository: LocalBookmarksRepository,
) : ViewModel() {
    private val _coursesUiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val coursesUiState = _coursesUiState.asStateFlow()

    private val loadCoursesTrigger = MutableSharedFlow<Unit>(replay = 1)
    private val coursesFlow = loadCoursesTrigger
        .flatMapLatest {
            flow {
                try {
                    val courses = coursesRepository.getCourses()
                    emit(courses)
                } catch (e: Exception) {
                    _coursesUiState.value = CoursesUiState.Error(e.message)
                }
            }
        }
    private val bookmarksFlow = bookmarksRepository.courses
    private val sortDescFlow = MutableStateFlow(false)

    init {
        combine(
            coursesFlow,
            bookmarksFlow,
            sortDescFlow,
        ) { courses, bookmarks, sortDesc ->
            val bookmarkIds = bookmarks.map { it.id }.toSet()
            val uiCourses = courses
                .sortedWith(
                    if (sortDesc) {
                        compareByDescending { it.publishDate }
                    } else {
                        compareBy { it.publishDate }
                    }
                ).map { course ->
                    CourseUiModel.fromCourse(
                        course = course,
                        isBookmarked = course.id in bookmarkIds,
                    )
                }
            _coursesUiState.value = CoursesUiState.Success(uiCourses)
        }.launchIn(viewModelScope)

        loadCourses()
    }

    fun loadCourses() {
        _coursesUiState.value = CoursesUiState.Loading
        viewModelScope.launch {
            loadCoursesTrigger.emit(Unit)
        }
    }

    fun toggleSortByPublishDateDesc() {
        sortDescFlow.value = !sortDescFlow.value
    }

    fun addBookmark(courseId: Int) {
        viewModelScope.launch {
            val courses = coursesFlow.firstOrNull() ?: return@launch
            val course = courses.firstOrNull { it.id == courseId } ?: return@launch
            bookmarksRepository.addCourse(course)
        }
    }

    fun removeBookmark(courseId: Int) {
        viewModelScope.launch {
            bookmarksRepository.removeCourse(courseId)
        }
    }
}
