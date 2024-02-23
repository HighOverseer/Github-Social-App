package com.fajar.githubuserappdicoding.detail_user.domain

import com.fajar.githubuserappdicoding.core.domain.common.StringRes
import com.fajar.githubuserappdicoding.core.domain.model.User

interface ToggleFavoriteStatusUseCase {
    suspend operator fun invoke(user: User, isUserFavorite: Boolean): StringRes
}