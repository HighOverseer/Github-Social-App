package com.fajar.githubuserappdicoding.core.domain.usecase

import com.fajar.githubuserappdicoding.core.domain.usecase.CheckIsThemeDarkUseCase
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsThemeDarkInteractor @Inject constructor(
    private val repository: Repository
) : CheckIsThemeDarkUseCase {
    override fun invoke(): Flow<Boolean> {
        return repository.isUsingDarkTheme()
    }
}