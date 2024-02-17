package com.fajar.githubuserappdicoding.domain.model

import com.fajar.githubuserappdicoding.data.local.db.UserFavorite


data class User(
    val username: String = "",
    val name: String = "",
    val avatar: String = "",
    val company: String = "",
    val location: String = "",
    val repository: String = "",
    val follower: String = "",
    val following: String = "",
    val githubUrl: String = ""
)