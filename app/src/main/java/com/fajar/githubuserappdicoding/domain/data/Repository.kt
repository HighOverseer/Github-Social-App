package com.fajar.githubuserappdicoding.domain.data

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.model.GithubRepos
import com.fajar.githubuserappdicoding.domain.model.User
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow

interface Repository {

    enum class DataType {
        FOLLOWER, FOLLOWING
    }

    suspend fun searchUser(query: String): Resource<List<UserPreview>>

    suspend fun getDetailUser(username: String): Resource<User>

    suspend fun getListGithubRepos(username: String): Resource<List<GithubRepos>>

    suspend fun getUserFollowersFollowing(
        username: String,
        dataType: DataType
    ): Resource<List<UserPreview>>

    fun getListUserFavorites(): Flow<List<UserPreview>>

    fun isUserInFav(username: String): Flow<Boolean>

    suspend fun addUserToFav(user: User): Long

    suspend fun removeUserFromFav(username: String)

    fun isUsingDarkTheme(): Flow<Boolean>

    suspend fun changeTheme()

    fun searchUserInFavorite(query: String): Flow<List<UserPreview>>

}