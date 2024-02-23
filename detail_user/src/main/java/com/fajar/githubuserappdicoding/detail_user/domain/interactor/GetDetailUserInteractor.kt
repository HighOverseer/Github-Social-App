package com.fajar.githubuserappdicoding.detail_user.domain.interactor

import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.detail_user.domain.GetDetailUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDetailUserInteractor @Inject constructor(
    private val repository: Repository
) : GetDetailUserUseCase {
    override fun invoke(username: String): Flow<Resource<User>> {
        return repository.getDetailUser(username)
    }
}