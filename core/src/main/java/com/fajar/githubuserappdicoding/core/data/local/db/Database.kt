package com.fajar.githubuserappdicoding.core.data.local.db

import androidx.room.RoomDatabase


@androidx.room.Database(
    entities = [UserFavorite::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun dao(): UserFavoriteDao


}