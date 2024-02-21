package com.fajar.githubuserappdicoding.detail_user.domain

import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.core.domain.common.StringRes

interface ToggleFavoriteStatusUseCase {
    suspend operator fun invoke(user: User, isUserFavorite: Boolean): StringRes
}