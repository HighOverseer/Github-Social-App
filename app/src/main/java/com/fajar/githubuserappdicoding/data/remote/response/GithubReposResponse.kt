package com.fajar.githubuserappdicoding.data.remote.response

import com.fajar.githubuserappdicoding.model.GithubRepos
import com.google.gson.annotations.SerializedName

data class GithubReposResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("watchers_count")
    val watchersCount: String?,
    @SerializedName("forks_count")
    val forksCount: String?
) {
    fun toGithubRepos(): GithubRepos {
        return GithubRepos(
            name ?: "",
            watchersCount ?: "",
            forksCount ?: ""
        )
    }
}

