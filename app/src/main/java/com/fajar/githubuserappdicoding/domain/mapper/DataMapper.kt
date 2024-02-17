package com.fajar.githubuserappdicoding.domain.mapper

import com.fajar.githubuserappdicoding.data.local.db.UserFavorite
import com.fajar.githubuserappdicoding.data.remote.response.DetailUserDto
import com.fajar.githubuserappdicoding.data.remote.response.GithubReposDto
import com.fajar.githubuserappdicoding.data.remote.response.SearchUserDto
import com.fajar.githubuserappdicoding.data.remote.response.UserModelDto
import com.fajar.githubuserappdicoding.domain.model.GithubRepos
import com.fajar.githubuserappdicoding.domain.model.User
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

object DataMapper {
    suspend fun mapDetailUserDtoToDomain(detailUserDto: DetailUserDto): User {
        return withContext(Dispatchers.Default) {
            ensureActive()

            detailUserDto.run {
                User(
                    username ?: "",
                    name ?: "",
                    avatarUrl ?: "",
                    company ?: "",
                    location ?: "",
                    reposTotal ?: "",
                    followersTotal ?: "",
                    followingTotal ?: "",
                    githubUrl ?: ""
                )
            }
        }
    }

    suspend fun mapGithubReposDtoToDomain(githubReposDto: GithubReposDto): GithubRepos {
        return withContext(Dispatchers.Default) {
            ensureActive()

            githubReposDto.run {
                GithubRepos(
                    name ?: "",
                    watchersCount ?: "",
                    forksCount ?: ""
                )
            }
        }
    }

    suspend fun mapSearchUserDtoToDomain(listUserDto: List<UserModelDto>):List<UserPreview>{
        return withContext(Dispatchers.Default){
            listUserDto.map {
                ensureActive()

                mapUserModelDtoToDomain(it)
            }
        }
    }

    fun mapUserToUserFavorite(user: User): UserFavorite {
        return user.run {
            UserFavorite(
                username,
                avatar,
                githubUrl
            )
        }
    }

    fun mapUserFavoriteToUserPreview(userFavorite: UserFavorite): UserPreview {
        return userFavorite.run {
            UserPreview(
                username,
                avatarUrl,
                githubUrl
            )
        }
    }


    private fun mapUserModelDtoToDomain(userModelDto: UserModelDto): UserPreview {
        return userModelDto.run {
            UserPreview(
                avatarUrl ?: "",
                username ?: "",
                githubUrl ?: ""
            )
        }
    }
}