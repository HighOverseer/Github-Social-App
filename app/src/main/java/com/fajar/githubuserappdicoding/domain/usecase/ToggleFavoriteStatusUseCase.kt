package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.StringRes
import com.fajar.githubuserappdicoding.domain.model.User

interface ToggleFavoriteStatusUseCase {
    suspend operator fun invoke(user: User, isUserFavorite: Boolean): StringRes
}