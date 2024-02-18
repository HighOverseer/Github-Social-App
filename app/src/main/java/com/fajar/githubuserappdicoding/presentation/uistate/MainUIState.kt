package com.fajar.githubuserappdicoding.presentation.uistate

import com.fajar.githubuserappdicoding.domain.common.SingleEvent
import com.fajar.githubuserappdicoding.domain.model.UserPreview

data class MainUIState(
    val query: String = "",
    val isLoading: Boolean = false,
    val listUserPreview: List<UserPreview> = emptyList(),
    //val toastMessage: SingleEvent<StringRes>? = null,
    val favoriteState: FavoriteState = FavoriteState()
)

class FavoriteState(
    val newIsFavoriteList: Boolean = false,
    oldIsFavoriteList: Boolean = false
) {
    val toggleSingleEvent: SingleEvent<Boolean>

    init {
        val isToggled = newIsFavoriteList != oldIsFavoriteList
        toggleSingleEvent = SingleEvent(isToggled)
    }
}