package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.data.Repository
import javax.inject.Inject

class ChangeThemePrefInteractor @Inject constructor(
    private val repository: Repository
):ChangeThemePrefUseCase {
    override suspend fun invoke() {
        repository.changeTheme()
    }
}