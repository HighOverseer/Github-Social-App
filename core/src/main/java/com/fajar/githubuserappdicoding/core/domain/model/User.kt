package com.fajar.githubuserappdicoding.core.domain.model


data class User constructor(
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