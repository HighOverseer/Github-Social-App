package com.fajar.githubuserappdicoding.detail_user.domain.interactor

import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.detail_user.domain.GetUserReposOrFollowingOrFollowerUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposOrFollowingOrFollowerInteractor @Inject constructor(
    private val repository: Repository
) : GetUserReposOrFollowingOrFollowerUseCase {

    override fun invoke(
        username: String,
        type: Repository.DataType?
    ): Flow<Resource<List<UserDetailInfo>>> {
        if (type == null) {
            return repository.getListGithubRepos(username)
        }

        return repository.getUserFollowersFollowing(username, type)
    }
}