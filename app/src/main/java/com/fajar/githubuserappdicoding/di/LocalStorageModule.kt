package com.fajar.githubuserappdicoding.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.fajar.githubuserappdicoding.data.local.db.Database
import com.fajar.githubuserappdicoding.data.local.db.UserFavoriteDao
import com.fajar.githubuserappdicoding.data.local.preference.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database"
        ).build()
    }

    @Provides
    fun provideUserFavoriteDao(database: Database):UserFavoriteDao{
        return database.dao()
    }

    @Provides
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
        return context.dataStore
    }
    @Provides
    fun provideDispactherDefault():CoroutineDispatcher = Dispatchers.Default

}

