package com.example.monotodo.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DefaultUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {

    private companion object {
        val IS_MEIGEN_DISPLAY_ENABLED = booleanPreferencesKey("is_meigen_display_enabled")
        const val TAG = "UserPreferencesRepo"
    }

    override val isMeigenDisplayEnabled: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_MEIGEN_DISPLAY_ENABLED] ?: true
        }

    override suspend fun setMeigenDisplayEnabled(isMeigenDisplayEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_MEIGEN_DISPLAY_ENABLED] = isMeigenDisplayEnabled
        }
    }
}
