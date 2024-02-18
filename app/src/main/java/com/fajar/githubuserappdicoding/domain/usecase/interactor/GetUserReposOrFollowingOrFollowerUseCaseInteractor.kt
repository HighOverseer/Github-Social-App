package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.domain.usecase.GetUserReposOrFollowingOrFollowerUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposOrFollowingOrFollowerUseCaseInteractor @Inject constructor(
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