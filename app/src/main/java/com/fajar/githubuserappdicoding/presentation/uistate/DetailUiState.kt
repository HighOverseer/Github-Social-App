package com.fajar.githubuserappdicoding.presentation.uistate

import com.fajar.githubuserappdicoding.domain.common.SingleEvent
import com.fajar.githubuserappdicoding.domain.common.StringRes
import com.fajar.githubuserappdicoding.domain.model.User

data class DetailUiState(
    val userProfile: User = User(),
    val isLoading: Boolean = false,
    val isUserFavorite: Boolean = false,
    val toastMessage: SingleEvent<StringRes>? = null
)