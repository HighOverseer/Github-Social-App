package com.fajar.githubuserappdicoding.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIsThemeDarkUseCase {
    operator fun invoke():Flow<Boolean>
}