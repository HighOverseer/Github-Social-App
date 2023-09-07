package com.fajar.githubuserappdicoding.domain

import androidx.lifecycle.LiveData
import com.fajar.githubuserappdicoding.data.local.db.UserFavorite
import com.fajar.githubuserappdicoding.model.GithubRepos
import com.fajar.githubuserappdicoding.model.User
import com.fajar.githubuserappdicoding.model.UserPreview
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

    fun getListUserFavorites(): LiveData<List<UserFavorite>>

    suspend fun isUserInFav(username: String): Boolean

    suspend fun addUserToFav(userFavorite: UserFavorite): Long

    suspend fun removeUserFromFav(username: String)

    fun isUsingDarkTheme(): Flow<Boolean>

    suspend fun changeTheme()

    fun searchUserUserInFavorite(query: String): LiveData<List<UserFavorite>>

}