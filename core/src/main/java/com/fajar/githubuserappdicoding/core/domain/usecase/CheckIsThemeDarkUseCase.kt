package com.fajar.githubuserappdicoding.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIsThemeDarkUseCase {
    operator fun invoke(): Flow<Boolean>
}