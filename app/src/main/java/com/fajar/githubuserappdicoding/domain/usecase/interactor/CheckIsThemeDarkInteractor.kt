package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsThemeDarkInteractor @Inject constructor(
    private val repository: Repository
) : CheckIsThemeDarkUseCase {
    override fun invoke(): Flow<Boolean> {
        return repository.isUsingDarkTheme()
    }
}