package com.fajar.githubuserappdicoding.data.remote.network

import com.fajar.githubuserappdicoding.data.remote.response.DetailUserDto
import com.fajar.githubuserappdicoding.data.remote.response.GithubReposDto
import com.fajar.githubuserappdicoding.data.remote.response.SearchUserDto
import com.fajar.githubuserappdicoding.data.remote.response.UserModelDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): SearchUserDto

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): DetailUserDto

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<UserModelDto>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<UserModelDto>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): List<GithubReposDto>
}