package com.fajar.githubuserappdicoding.detail_user.domain

import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo
import kotlinx.coroutines.flow.Flow

interface GetUserReposOrFollowingOrFollowerUseCase {
    operator fun invoke(
        username: String,
        type: Repository.DataType? = null
    ): Flow<Resource<List<UserDetailInfo>>>
}