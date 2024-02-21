package com.fajar.githubuserappdicoding.core.domain.data


import kotlinx.coroutines.flow.Flow

interface SettingPreference {
    fun isUsingDarkTheme(): Flow<Boolean>
    suspend fun changeTheme()
}