package com.fajar.githubuserappdicoding.presentation.uistate

import com.fajar.githubuserappdicoding.domain.model.UserDetailInfo

data class UserDetailInfoUiState(
    val listItems: List<UserDetailInfo> = emptyList(),
    val isLoading: Boolean = false,
    //val toastMessage: SingleEvent<StringRes>? = null
)