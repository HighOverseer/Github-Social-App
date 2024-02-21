package com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.interactor

import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.ChangeThemePrefUseCase
import javax.inject.Inject

class ChangeThemePrefInteractor @Inject constructor(
    private val repository: Repository
) : ChangeThemePrefUseCase {
    override suspend fun invoke() {
        repository.changeTheme()
    }
}