package com.fajar.githubuserappdicoding.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fajar.githubuserappdicoding.model.UserPreview

@Entity("User_Favorite")
data class UserFavorite(
    @ColumnInfo(name = "username")
    @PrimaryKey
    val username: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "github_url")
    val githubUrl: String
) {
    fun toUserPreview(): UserPreview {
        return UserPreview(
            avatarUrl,
            username,
            githubUrl
        )
    }
}