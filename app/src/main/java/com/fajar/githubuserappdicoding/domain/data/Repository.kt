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

    fun searchUser(query: String): Flow<Resource<List<UserPreview>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun getListGithubRepos(username: String): Flow<Resource<List<GithubRepos>>>

    fun getUserFollowersFollowing(
        username: String,
        dataType: DataType
    ): Flow<Resource<List<UserPreview>>>

    fun getListUserFavorites(): Flow<List<UserPreview>>

    fun isUserInFav(username: String): Flow<Boolean>

    suspend fun addUserToFav(user: User): Long

    suspend fun removeUserFromFav(username: String)

    fun isUsingDarkTheme(): Flow<Boolean>

    suspend fun changeTheme()

    fun searchUserInFavorite(query: String): Flow<List<UserPreview>>

}