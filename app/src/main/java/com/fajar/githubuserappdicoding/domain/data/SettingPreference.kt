package com.fajar.githubuserappdicoding.domain.data


import kotlinx.coroutines.flow.Flow

interface SettingPreference {
    fun isUsingDarkTheme(): Flow<Boolean>
    suspend fun changeTheme()
}