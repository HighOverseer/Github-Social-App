package com.fajar.githubuserappdicoding.di

import com.fajar.githubuserappdicoding.data.RepositoryImpl
import com.fajar.githubuserappdicoding.data.local.preference.SettingPreferenceImpl
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.data.SettingPreference
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl):Repository

    @Binds
    abstract fun provideUserPreference(settingPreferenceImpl: SettingPreferenceImpl):SettingPreference

}