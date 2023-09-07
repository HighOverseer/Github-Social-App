package com.fajar.githubuserappdicoding.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("settings")

class SettingPreference private constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun isUsingDarkTheme(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[isDarkTheme] ?: false
        }
    }

    suspend fun changeTheme() {
        dataStore.edit { prefs ->
            val oldPref = prefs[isDarkTheme] ?: false
            prefs[isDarkTheme] = !oldPref
        }
    }

    companion object {
        private val isDarkTheme = booleanPreferencesKey("is_dark_theme")

        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingPreference(dataStore)
            }.also { INSTANCE = it }
        }
    }
}