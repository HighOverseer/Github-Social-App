package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserDetailInfo
import kotlinx.coroutines.flow.Flow

interface GetUserReposOrFollowingOrFollowerUseCase {
    operator fun invoke(
        username: String,
        type: Repository.DataType? = null
    ): Flow<Resource<List<UserDetailInfo>>>
}