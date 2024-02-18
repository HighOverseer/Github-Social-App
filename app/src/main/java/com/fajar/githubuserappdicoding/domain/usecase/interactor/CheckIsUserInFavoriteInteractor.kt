package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject


class CheckIsUserInFavoriteInteractor @Inject constructor(
    private val repository: Repository
) : CheckIsUserInFavoriteUseCase {
    override fun invoke(username: String): Flow<Boolean> {
        return repository.isUserInFav(username)
            .distinctUntilChanged()
    }
}