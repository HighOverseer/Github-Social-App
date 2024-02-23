package com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.fajar.githubuserappdicoding.core.di.util.ViewModelAssistedFactory
import com.fajar.githubuserappdicoding.detail_user.domain.GetUserReposOrFollowingOrFollowerUseCase
import javax.inject.Inject

class UserDetailInfoViewModelFactory @Inject constructor(
    private val getUserReposOrFollowingOrFollowerUseCase: GetUserReposOrFollowingOrFollowerUseCase,
) : ViewModelAssistedFactory<UserDetailInfoVM> {
    override fun create(savedStateHandle: SavedStateHandle): UserDetailInfoVM {
        return UserDetailInfoVM(
            getUserReposOrFollowingOrFollowerUseCase,
            savedStateHandle
        )
    }
}