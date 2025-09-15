package com.example.courses.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.courses.navigation.Route

class CourseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val courseDetails = savedStateHandle.toRoute<Route.CourseDetails>()
    val courseId = courseDetails.id
}
