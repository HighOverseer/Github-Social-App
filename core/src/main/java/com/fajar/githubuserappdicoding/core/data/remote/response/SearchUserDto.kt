package com.fajar.githubuserappdicoding.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchUserDto(
    @field:SerializedName("items")
    val items: List<UserModelDto>
)

@Keep
data class UserModelDto(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("html_url")
    val githubUrl: String?
)


