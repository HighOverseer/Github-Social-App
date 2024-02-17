package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.User
import javax.inject.Inject


class GetDetailUserInteractor @Inject constructor(
    private val repository: Repository
):GetDetailUserUseCase {
    override suspend fun invoke(username: String): Resource<User> {
        return repository.getDetailUser(username)
    }
}