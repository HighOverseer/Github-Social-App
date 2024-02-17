package com.fajar.githubuserappdicoding.domain.usecase

import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.domain.common.SingleEvent
import com.fajar.githubuserappdicoding.domain.common.StaticString
import com.fajar.githubuserappdicoding.domain.common.StringRes
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.User
import javax.inject.Inject

class ToggleFavoriteStatusInteractor @Inject constructor(
    private val repository: Repository
):ToggleFavoriteStatusUseCase{

    override suspend fun invoke(user: User, isUserFavorite: Boolean): SingleEvent<StringRes> {
        val newIsUserFavorite = !isUserFavorite
        return if (newIsUserFavorite){
            repository.addUserToFav(user)
            SingleEvent(StaticString(R.string.added_to_favorite_info, user.username))
        }else {
            repository.removeUserFromFav(user.username)
            SingleEvent(StaticString(R.string.removed_from_favorite_info, user.username))
        }

    }
}