package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.usecase.ChangeThemePrefUseCase
import javax.inject.Inject

class ChangeThemePrefInteractor @Inject constructor(
    private val repository: Repository
) : ChangeThemePrefUseCase {
    override suspend fun invoke() {
        repository.changeTheme()
    }
}