package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserInFavoriteInteractor @Inject constructor(
    private val repository: Repository
):SearchUserInFavoriteUseCase {

    override fun invoke(query: String): Flow<List<UserPreview>> {
        return repository.searchUserInFavorite(query)
    }
}