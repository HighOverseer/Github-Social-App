package com.fajar.githubuserappdicoding.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.fajar.githubuserappdicoding.core.R
import com.fajar.githubuserappdicoding.core.data.local.db.Database
import com.fajar.githubuserappdicoding.core.data.local.db.UserFavoriteDao
import com.fajar.githubuserappdicoding.core.data.local.preference.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        val passphrase:ByteArray = SQLiteDatabase.getBytes(
            context.getString(R.string.app_name).toCharArray()
        )
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideUserFavoriteDao(database: Database): UserFavoriteDao {
        return database.dao()
    }

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

}

