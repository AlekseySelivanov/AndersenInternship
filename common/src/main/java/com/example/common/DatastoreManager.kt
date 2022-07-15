package com.example.common

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    private val context: Context
) {

   suspend fun addToDatastore(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    suspend fun removeFromDataStore(key: Preferences.Key<String>) {
        context.dataStore.edit { settings ->
            settings.remove(key)
        }
    }

    fun observeKeyValue(key: Preferences.Key<String>): Flow<String?> {
        return context.dataStore.data.map {preferences ->
            preferences[key]
        }
    }
}