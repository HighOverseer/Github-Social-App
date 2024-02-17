package com.fajar.githubuserappdicoding.domain.usecase

import kotlinx.coroutines.flow.Flow

interface CheckIsUserInFavoriteUseCase {

    operator fun invoke(username:String):Flow<Boolean>
}