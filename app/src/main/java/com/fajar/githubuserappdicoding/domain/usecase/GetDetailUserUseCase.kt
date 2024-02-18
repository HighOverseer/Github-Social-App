package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetDetailUserUseCase {
    operator fun invoke(username: String): Flow<Resource<User>>
}