package com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase

import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface SearchUserUseCase {
    operator fun invoke(isInFavorite: Boolean, query: String): Flow<Resource<List<UserPreview>>>
}
