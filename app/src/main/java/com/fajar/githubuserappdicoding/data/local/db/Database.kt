package com.fajar.githubuserappdicoding.data.local.db

import androidx.room.RoomDatabase


@androidx.room.Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun dao(): UserFavoriteDao


}