package com.fajar.githubuserappdicoding.core.data


import com.fajar.githubuserappdicoding.core.R
import com.fajar.githubuserappdicoding.core.data.local.db.UserFavoriteDao
import com.fajar.githubuserappdicoding.core.data.remote.network.ApiService
import com.fajar.githubuserappdicoding.core.domain.common.DynamicString
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.common.StaticString
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.data.SettingPreference
import com.fajar.githubuserappdicoding.core.domain.mapper.DataMapper
import com.fajar.githubuserappdicoding.core.domain.model.GithubRepos
import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: UserFavoriteDao,
    private val settingPreference: SettingPreference
) : Repository {

    private fun <T, S> fetchData(
        fetch: suspend () -> T,
        mapData: suspend T.() -> S,
    ): Flow<Resource<S>> = flow {
        try {

            val data = fetch.invoke()

            val result = mapData.invoke(data)

            emit(
                Resource.Success(
                    result
                )
            )
        } catch (e: HttpException) {
            emit(
                e.response()?.errorBody()?.string()?.let {
                    Resource.Failure(
                        DynamicString(it)
                    )
                } ?: Resource.Failure(
                    StaticString(
                        R.string.response_not_success
                    )
                )
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e

            emit(
                Resource.Error(e)
            )
        }
    }.flowOn(Dispatchers.IO)


    override fun searchUser(query: String): Flow<Resource<List<UserPreview>>> {
        return fetchData(
            fetch = {
                apiService.searchUser(query)
            },
            mapData = {
                DataMapper.mapSearchUserDtoToDomain(items)
            }
        )
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return fetchData(
            fetch = { apiService.getDetailUser(username) },
            mapData = DataMapper::mapDetailUserDtoToDomain
        )
    }

    override fun getListGithubRepos(username: String): Flow<Resource<List<GithubRepos>>> {
        return fetchData(
            fetch = {
                apiService.getUserRepos(username)
            },
            mapData = {
                map {
                    DataMapper.mapGithubReposDtoToDomain(it)
                }
            }
        )
    }

    override fun getUserFollowersFollowing(
        username: String,
        dataType: Repository.DataType
    ): Flow<Resource<List<UserPreview>>> {
        val fetch = when (dataType) {
            Repository.DataType.FOLLOWER -> {
                suspend { apiService.getUserFollowers(username) }
            }

            Repository.DataType.FOLLOWING -> {
                suspend { apiService.getUserFollowing(username) }
            }
        }
        return fetchData(
            fetch = fetch,
            mapData = DataMapper::mapSearchUserDtoToDomain
        )
    }

    override fun getListUserFavorites(): Flow<List<UserPreview>> {
        return dao.searchUserInFavorite("").map { list ->
            list.map {
                DataMapper.mapUserFavoriteToUserPreview(it)
            }
        }.flowOn(Dispatchers.Default)
    }

    override fun isUserInFav(username: String) = dao.isUserInFavorites(username)

    override suspend fun addUserToFav(user: User): Long {
        return dao.addUserToFav(
            DataMapper.mapUserToUserFavorite(user)
        )
    }

    override suspend fun removeUserFromFav(username: String) = dao.removeUserFromFav(username)

    override fun isUsingDarkTheme(): Flow<Boolean> = settingPreference.isUsingDarkTheme()

    override suspend fun changeTheme() = settingPreference.changeTheme()

    override fun searchUserInFavorite(query: String): Flow<List<UserPreview>> {
        return dao.searchUserInFavorite(query).map { list ->
            list.map {
                DataMapper.mapUserFavoriteToUserPreview(it)
            }
        }.flowOn(Dispatchers.Default)
    }
}