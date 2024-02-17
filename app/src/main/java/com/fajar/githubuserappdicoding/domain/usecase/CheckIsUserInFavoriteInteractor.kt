package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject


class CheckIsUserInFavoriteInteractor @Inject constructor(
    private val repository: Repository
):CheckIsUserInFavoriteUseCase {
    override fun invoke(username:String): Flow<Boolean> {
        return repository.isUserInFav(username)
            .distinctUntilChanged()
    }
}