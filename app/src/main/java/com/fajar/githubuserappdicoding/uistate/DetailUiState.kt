package com.fajar.githubuserappdicoding.uistate

import com.fajar.githubuserappdicoding.model.User
import com.fajar.githubuserappdicoding.domain.SingleEvent
import com.fajar.githubuserappdicoding.domain.StringRes

data class DetailUiState(
    val userProfile: User = User(),
    val isLoading: Boolean = false,
    val isUserFavorite: Boolean = false,
    val toastMessage: SingleEvent<StringRes>? = null
)