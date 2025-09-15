package com.example.courses.feature.bookmarks.data.local.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val DATASTORE_NAME = "bookmarks"

val Context.bookmarksDataStore by preferencesDataStore(name = DATASTORE_NAME)
