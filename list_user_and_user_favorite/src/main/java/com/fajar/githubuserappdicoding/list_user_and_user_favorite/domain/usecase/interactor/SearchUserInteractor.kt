package com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.interactor

import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.SearchUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(
    private val repository: Repository
) : SearchUserUseCase {
    override fun invoke(isInFavorite: Boolean, query: String): Flow<Resource<List<UserPreview>>> {
        return if (isInFavorite) {
            repository.searchUserInFavorite(query).map { list ->
                Resource.Success(list)
            }
        } else {
            repository.searchUser(query)
        }
    }
}