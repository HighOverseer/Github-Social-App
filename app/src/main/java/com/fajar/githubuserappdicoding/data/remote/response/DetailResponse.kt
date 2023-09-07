package com.fajar.githubuserappdicoding.data.remote.response

import com.fajar.githubuserappdicoding.model.User
import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("public_repos")
    val reposTotal: String?,
    @field:SerializedName("followers")
    val followersTotal: String?,
    @field:SerializedName("following")
    val followingTotal: String?,
    @field:SerializedName("html_url")
    val githubUrl: String?
) {
    fun toUser(): User {
        return User(
            username ?: "",
            name ?: "",
            avatarUrl ?: "",
            company ?: "",
            location ?: "",
            reposTotal ?: "",
            followersTotal ?: "",
            followingTotal ?: "",
            githubUrl ?: ""
        )
    }
}