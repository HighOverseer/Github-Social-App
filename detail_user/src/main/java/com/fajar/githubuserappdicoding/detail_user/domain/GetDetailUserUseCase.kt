package com.fajar.githubuserappdicoding.detail_user.domain

import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetDetailUserUseCase {
    operator fun invoke(username: String): Flow<Resource<User>>
}