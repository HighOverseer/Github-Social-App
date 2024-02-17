package com.fajar.githubuserappdicoding.di

import android.content.Context
import com.fajar.githubuserappdicoding.data.RepositoryImpl
import com.fajar.githubuserappdicoding.data.local.db.Database
import com.fajar.githubuserappdicoding.data.local.preference.SettingPreference
import com.fajar.githubuserappdicoding.data.local.preference.dataStore
import com.fajar.githubuserappdicoding.data.remote.network.ApiClient
import com.fajar.githubuserappdicoding.domain.data.Repository


object Injection {
    fun provideRepos(applicationContext: Context): Repository {
        applicationContext.apply {
            val database = Database.getInstance(this)
            val settingPreference = SettingPreference.getInstance(this.dataStore)
            return RepositoryImpl.getInstance(
                ApiClient.getApiService(),
                database.dao(),
                settingPreference
            )
        }

    }
}