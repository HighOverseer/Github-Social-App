package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.model.User

interface GetDetailUserUseCase{
    suspend operator fun invoke(username:String):Resource<User>
}