package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(
    private val repository: Repository
) : SearchUserUseCase {
    override suspend fun invoke(query: String): Flow<Resource<List<UserPreview>>> {
        return repository.searchUser(query)
    }
}