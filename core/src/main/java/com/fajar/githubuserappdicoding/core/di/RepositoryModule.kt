package com.fajar.githubuserappdicoding.core.di

import com.fajar.githubuserappdicoding.core.domain.data.SettingPreference
import com.fajar.githubuserappdicoding.core.data.local.preference.SettingPreferenceImpl
import com.fajar.githubuserappdicoding.core.data.RepositoryImpl
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    abstract fun provideUserPreference(settingPreferenceImpl: SettingPreferenceImpl): SettingPreference

}