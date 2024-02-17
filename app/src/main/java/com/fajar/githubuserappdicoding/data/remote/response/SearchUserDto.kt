package com.fajar.githubuserappdicoding.data.remote.response

import com.fajar.githubuserappdicoding.domain.model.UserPreview
import com.google.gson.annotations.SerializedName

data class SearchUserDto(
    @field:SerializedName("items")
    val items: List<UserModelDto>
)

data class UserModelDto(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("html_url")
    val githubUrl: String?
)


