package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow

interface SearchUserInFavoriteUseCase {
    operator fun invoke(query:String = ""): Flow<List<UserPreview>>
}