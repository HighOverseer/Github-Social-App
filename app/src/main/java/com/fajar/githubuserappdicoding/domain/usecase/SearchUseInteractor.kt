package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface SearchUseCase{
    operator fun invoke(isInFavorite:Boolean, query:String): Flow<Resource<List<UserPreview>>>
}
class SearchInteractor(
    private val repository: Repository
):SearchUseCase {
    override fun invoke(isInFavorite: Boolean, query:String): Flow<Resource<List<UserPreview>>> = flow {
        if (isInFavorite) {
            emitAll(
                repository.searchUserInFavorite(query).map {list ->
                    Resource.Success(list)
                }
            )
        }else {
            emit(
                repository.searchUser(query)
            )
        }
    }
}