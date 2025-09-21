package com.example.courses.feature.home.data.remote.datasource

import com.example.courses.feature.home.data.remote.model.CoursesDto
import retrofit2.http.GET

const val BASE_URL = "https://courses.free.beeceptor.com/"

interface ApiService {
    @GET("courses")
    suspend fun getCourses(): CoursesDto
}
