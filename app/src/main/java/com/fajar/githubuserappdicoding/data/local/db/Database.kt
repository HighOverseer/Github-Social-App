package com.fajar.githubuserappdicoding.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


@androidx.room.Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun dao(): UserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        @JvmStatic
        fun getInstance(context: Context): Database {
            if (INSTANCE == null) {
                synchronized(Database::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "database"
                    ).build()
                }
            }
            return INSTANCE as Database
        }
    }

}