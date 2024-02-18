package com.fajar.githubuserappdicoding.domain.usecase.interactor

import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.domain.common.StaticString
import com.fajar.githubuserappdicoding.domain.common.StringRes
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.User
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusUseCase
import javax.inject.Inject

class ToggleFavoriteStatusInteractor @Inject constructor(
    private val repository: Repository
) : ToggleFavoriteStatusUseCase {

    override suspend fun invoke(user: User, isUserFavorite: Boolean): StringRes {
        val newIsUserFavorite = !isUserFavorite
        return if (newIsUserFavorite) {
            repository.addUserToFav(user)
            StaticString(R.string.added_to_favorite_info, user.username)
        } else {
            repository.removeUserFromFav(user.username)
            StaticString(R.string.removed_from_favorite_info, user.username)
        }

    }
}