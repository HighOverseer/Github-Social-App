package com.fajar.githubuserappdicoding.detail_user.presentation.uistate

import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo

data class UserDetailInfoUiState(
    val listItems: List<UserDetailInfo> = emptyList(),
    val isLoading: Boolean = false,
)