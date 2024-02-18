package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.User
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDetailUserInteractor @Inject constructor(
    private val repository: Repository
) : GetDetailUserUseCase {
    override fun invoke(username: String): Flow<Resource<User>> {
        return repository.getDetailUser(username)
    }
}