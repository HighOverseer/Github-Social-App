package com.fajar.githubuserappdicoding.detail_user.domain

import kotlinx.coroutines.flow.Flow

interface CheckIsUserInFavoriteUseCase {

    operator fun invoke(username: String): Flow<Boolean>
}