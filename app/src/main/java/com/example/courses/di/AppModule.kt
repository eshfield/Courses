package com.example.courses.di

import com.example.courses.feature.bookmarks.data.local.datasource.bookmarksDataStore
import com.example.courses.feature.bookmarks.data.local.repository.LocalBookmarksRepository
import com.example.courses.feature.bookmarks.domain.repository.BookmarksRepository
import com.example.courses.feature.bookmarks.presentation.BookmarksViewModel
import com.example.courses.feature.home.presentation.CourseDetailsViewModel
import com.example.courses.feature.home.presentation.HomeScreenViewModel
import com.example.courses.feature.home.data.remote.datasource.ApiService
import com.example.courses.feature.home.data.remote.datasource.BASE_URL
import com.example.courses.feature.home.data.remote.repository.RemoteCoursesRepository
import com.example.courses.feature.home.domain.repository.CoursesRepository
import com.example.courses.feature.signin.presentation.SignInViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    viewModelOf(::SignInViewModel)

    single {
        val contentType = "application/json; charset=UTF8".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }
    singleOf(::RemoteCoursesRepository) { bind<CoursesRepository>() }
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::CourseDetailsViewModel)

    single { androidContext().bookmarksDataStore }
    singleOf(::LocalBookmarksRepository) { bind<BookmarksRepository>() }
    viewModelOf(::BookmarksViewModel)
}
