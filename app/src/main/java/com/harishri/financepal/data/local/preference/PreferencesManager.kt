package com.harishri.financepal.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Creates a single instance of DataStore for the entire application
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        // Defines the key for the auth token
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    /**
     * Retrieves the authentication token synchronously.
     * Use with caution, as this blocks the thread.
     */
    suspend fun getAuthToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }.first()
    }

    /**
     * Saves the authentication token.
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

}