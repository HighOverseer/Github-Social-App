package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import kotlinx.coroutines.flow.Flow

interface SearchUserUseCase {
    suspend operator fun invoke(query: String = ""): Flow<Resource<List<UserPreview>>>
}