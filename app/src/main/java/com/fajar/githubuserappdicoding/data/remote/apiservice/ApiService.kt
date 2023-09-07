package com.fajar.githubuserappdicoding.data.remote.apiservice

import com.fajar.githubuserappdicoding.data.remote.response.DetailResponse
import com.fajar.githubuserappdicoding.data.remote.response.GithubReposResponse
import com.fajar.githubuserappdicoding.data.remote.response.SearchResponse
import com.fajar.githubuserappdicoding.data.remote.response.UserModelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserModelResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserModelResponse>>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Call<List<GithubReposResponse>>
}