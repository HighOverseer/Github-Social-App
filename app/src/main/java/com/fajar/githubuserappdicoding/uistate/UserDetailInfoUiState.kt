package com.fajar.githubuserappdicoding.uistate

import com.fajar.githubuserappdicoding.domain.SingleEvent
import com.fajar.githubuserappdicoding.domain.StringRes
import com.fajar.githubuserappdicoding.model.UserDetailInfo

data class UserDetailInfoUiState(
    val listItems: List<UserDetailInfo> = emptyList(),
    val isLoading: Boolean = false,
    val toastMessage: SingleEvent<StringRes>? = null
)