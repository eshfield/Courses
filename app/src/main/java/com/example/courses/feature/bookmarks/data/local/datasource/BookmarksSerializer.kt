package com.example.courses.feature.bookmarks.data.local.datasource

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class BookmarksSerializer<T>(
    keyName: String,
    private val serializer: KSerializer<T>,
) {
    val key = stringPreferencesKey(keyName)
    private val json = Json { encodeDefaults = true }

    fun serialize(list: List<T>) = json.encodeToString(
        serializer = ListSerializer(serializer),
        value = list,
    )

    fun deserialize(prefs: Preferences): List<T> {
        val string = prefs[key] ?: return emptyList()
        return runCatching {
            json.decodeFromString(
                deserializer = ListSerializer(serializer),
                string = string,
            )
        }.getOrElse { emptyList() }
    }
}
