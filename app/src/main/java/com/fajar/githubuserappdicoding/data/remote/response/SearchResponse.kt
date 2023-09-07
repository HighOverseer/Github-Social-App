package com.fajar.githubuserappdicoding.data.remote.response

import com.fajar.githubuserappdicoding.model.UserPreview
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("items")
    val items: List<UserModelResponse>
)

data class UserModelResponse(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("html_url")
    val githubUrl: String?
) {
    fun toUserPreview(): UserPreview {
        return UserPreview(
            avatarUrl ?: "",
            username ?: "",
            githubUrl ?: ""
        )
    }
}


