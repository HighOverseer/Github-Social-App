package com.fajar.githubuserappdicoding.core.domain.model

sealed class UserDetailInfo

data class UserPreview(
    val avatarUrl: String,
    val username: String,
    val githubUrl: String
) : UserDetailInfo()

data class GithubRepos(
    val name: String,
    val watchersCount: String,
    val forksCount: String
) : UserDetailInfo()