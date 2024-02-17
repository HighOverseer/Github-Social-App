package com.fajar.githubuserappdicoding.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.fajar.githubuserappdicoding.domain.data.SettingPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore by preferencesDataStore("settings")

@Singleton
class SettingPreferenceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingPreference{

    override fun isUsingDarkTheme(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[isDarkTheme] ?: false
        }
    }

    override suspend fun changeTheme() {
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
                INSTANCE ?: SettingPreferenceImpl(dataStore)
            }.also { INSTANCE = it }
        }
    }
}