package com.fajar.githubuserappdicoding.data.remote

import androidx.lifecycle.LiveData
import com.fajar.githubuserappdicoding.model.User
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.data.local.db.UserFavorite
import com.fajar.githubuserappdicoding.data.local.db.UserFavoriteDao
import com.fajar.githubuserappdicoding.data.local.preference.SettingPreference
import com.fajar.githubuserappdicoding.data.remote.apiservice.ApiService
import com.fajar.githubuserappdicoding.data.remote.response.DetailResponse
import com.fajar.githubuserappdicoding.data.remote.response.GithubReposResponse
import com.fajar.githubuserappdicoding.data.remote.response.SearchResponse
import com.fajar.githubuserappdicoding.data.remote.response.UserModelResponse
import com.fajar.githubuserappdicoding.domain.DynamicString
import com.fajar.githubuserappdicoding.domain.Repository
import com.fajar.githubuserappdicoding.domain.Resource
import com.fajar.githubuserappdicoding.domain.StaticString
import com.fajar.githubuserappdicoding.model.GithubRepos
import com.fajar.githubuserappdicoding.model.UserPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.awaitResponse

class RepositoryImpl private constructor(
    private val apiService: ApiService,
    private val dao: UserFavoriteDao,
    private val settingPreference: SettingPreference
) : Repository {

    private suspend fun <T, S> fetchData(
        execute: () -> Call<T>,
        transformData: (T) -> S
    ): Resource<S> = withContext(Dispatchers.IO) {
        try {
            val response = execute().awaitResponse()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(
                    transformData(responseBody)
                )
            } else {
                val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                jsonObject?.let {
                    val message = jsonObject.getString("message")
                    Resource.Failure(
                        DynamicString(message)
                    )
                } ?: Resource.Failure(
                    StaticString(
                        R.string.response_not_success
                    )
                )
            }
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    override suspend fun searchUser(query: String): Resource<List<UserPreview>> =
        withContext(Dispatchers.Default) {
            val execute = { apiService.searchUser(query) }
            val transformData = { response: SearchResponse ->
                val listUserPreview = mutableListOf<UserPreview>()
                response.items.forEach {
                    listUserPreview.add(it.toUserPreview())
                }
                listUserPreview
            }
            fetchData(execute, transformData)
        }

    override suspend fun getDetailUser(username: String): Resource<User> =
        withContext(Dispatchers.Default) {
            val execute = { apiService.getDetailUser(username) }
            val transformData = { response: DetailResponse ->
                response.toUser()
            }
            fetchData(execute, transformData)
        }

    override suspend fun getListGithubRepos(username: String): Resource<List<GithubRepos>> =
        withContext(Dispatchers.Default) {
            val execute = { apiService.getUserRepos(username) }
            val transformData = { response: List<GithubReposResponse> ->
                val listGithubRepos = mutableListOf<GithubRepos>()
                response.forEach {
                    listGithubRepos.add(it.toGithubRepos())
                }
                listGithubRepos
            }
            fetchData(execute, transformData)
        }

    override suspend fun getUserFollowersFollowing(
        username: String,
        dataType: Repository.DataType
    ): Resource<List<UserPreview>> = withContext(Dispatchers.Default) {
        val execute = when (dataType) {
            Repository.DataType.FOLLOWER -> {
                { apiService.getUserFollowers(username) }
            }

            Repository.DataType.FOLLOWING -> {
                { apiService.getUserFollowing(username) }
            }
        }
        val transformData = { response: List<UserModelResponse> ->
            val userFollowers = mutableListOf<UserPreview>()
            response.forEach { userFollowers.add(it.toUserPreview()) }
            userFollowers
        }
        fetchData(execute, transformData)
    }

    override fun getListUserFavorites() = dao.getUserFavorites()

    override suspend fun isUserInFav(username: String) = dao.isUserInFavorites(username)

    override suspend fun addUserToFav(userFavorite: UserFavorite) = dao.addUserToFav(userFavorite)

    override suspend fun removeUserFromFav(username: String) = dao.removeUserFromFav(username)

    override fun isUsingDarkTheme(): Flow<Boolean> = settingPreference.isUsingDarkTheme()

    override suspend fun changeTheme() = settingPreference.changeTheme()

    override fun searchUserUserInFavorite(query: String): LiveData<List<UserFavorite>> {
        return dao.searchUserInFavorite(query)
    }

    companion object {
        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(
            apiService: ApiService,
            dao: UserFavoriteDao,
            settingPreference: SettingPreference
        ): RepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                RepositoryImpl(apiService, dao, settingPreference)
            }.also { INSTANCE = it }
        }
    }
}