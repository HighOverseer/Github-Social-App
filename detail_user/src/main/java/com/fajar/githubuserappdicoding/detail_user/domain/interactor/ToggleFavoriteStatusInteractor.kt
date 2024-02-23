package com.fajar.githubuserappdicoding.detail_user.domain.interactor

import com.fajar.githubuserappdicoding.core.domain.common.StaticString
import com.fajar.githubuserappdicoding.core.domain.common.StringRes
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.model.User
import com.fajar.githubuserappdicoding.detail_user.R
import com.fajar.githubuserappdicoding.detail_user.domain.ToggleFavoriteStatusUseCase
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