package com.fajar.githubuserappdicoding.detail_user.presentation.uistate

import com.fajar.githubuserappdicoding.core.domain.model.User

data class DetailUiState(
    val userProfile: User = User(),
    val isLoading: Boolean = false,
    val isUserFavorite: Boolean = false,
    //val toastMessage: SingleEvent<StringRes>? = null
)