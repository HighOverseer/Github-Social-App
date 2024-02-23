package com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.fajar.githubuserappdicoding.core.di.util.ViewModelAssistedFactory
import com.fajar.githubuserappdicoding.detail_user.domain.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.ToggleFavoriteStatusUseCase
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    private val toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase,
    private val getDetailUserUseCase: GetDetailUserUseCase,
    private val checkIsUserInFavoriteUseCase: CheckIsUserInFavoriteUseCase
) : ViewModelAssistedFactory<DetailVM> {
    override fun create(savedStateHandle: SavedStateHandle): DetailVM {
        return DetailVM(
            toggleFavoriteStatusUseCase,
            getDetailUserUseCase,
            checkIsUserInFavoriteUseCase,
            savedStateHandle
        )
    }
}