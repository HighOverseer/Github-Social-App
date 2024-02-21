package com.fajar.githubuserappdicoding.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubReposDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("watchers_count")
    val watchersCount: String?,
    @SerializedName("forks_count")
    val forksCount: String?
)

