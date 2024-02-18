package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SearchUseCase {
    operator fun invoke(isInFavorite: Boolean, query: String): Flow<Resource<List<UserPreview>>>
}

class SearchInteractor @Inject constructor(
    private val repository: Repository
) : SearchUseCase {
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