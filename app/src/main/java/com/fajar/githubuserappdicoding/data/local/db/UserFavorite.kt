package com.fajar.githubuserappdicoding.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("User_Favorite")
data class UserFavorite(
    @ColumnInfo(name = "username")
    @PrimaryKey
    val username: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "github_url")
    val githubUrl: String
)