package com.fajar.githubuserappdicoding.detail_user.di

import androidx.lifecycle.ViewModel
import com.fajar.githubuserappdicoding.core.di.dynamicfeature.ViewModelKey
import com.fajar.githubuserappdicoding.detail_user.domain.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.GetUserReposOrFollowingOrFollowerUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.ToggleFavoriteStatusUseCase
import com.fajar.githubuserappdicoding.detail_user.domain.interactor.CheckIsUserInFavoriteInteractor
import com.fajar.githubuserappdicoding.detail_user.domain.interactor.GetDetailUserInteractor
import com.fajar.githubuserappdicoding.detail_user.domain.interactor.GetUserReposOrFollowingOrFollowerInteractor
import com.fajar.githubuserappdicoding.detail_user.domain.interactor.ToggleFavoriteStatusInteractor
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.DetailVM
import com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel.UserDetailInfoVM
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@DisableInstallInCheck
@Module
abstract class DetailUserModule {

    @Binds
    abstract fun provideGetDetailUserUseCase(getDetailUserInteractor: GetDetailUserInteractor): GetDetailUserUseCase

    @Binds
    abstract fun provideToggleFavoriteStatusUseCase(toggleFavoriteStatusInteractor: ToggleFavoriteStatusInteractor): ToggleFavoriteStatusUseCase

    @Binds
    abstract fun provideCheckIsUserInFavoriteUseCase(checkIsUserInFavoriteInteractor: CheckIsUserInFavoriteInteractor): CheckIsUserInFavoriteUseCase

    @Binds
    abstract fun provideGetUserReposOrFollowingOrFollowerUseCase(
        getUserReposOrFollowingOrFollowerUseCaseInteractor: GetUserReposOrFollowingOrFollowerInteractor
    ): GetUserReposOrFollowingOrFollowerUseCase
}
/*
@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailUserModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetDetailUserUseCase(getDetailUserInteractor: GetDetailUserInteractor): GetDetailUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideToggleFavoriteStatusUseCase(toggleFavoriteStatusInteractor: ToggleFavoriteStatusInteractor): ToggleFavoriteStatusUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckIsUserInFavoriteUseCase(checkIsUserInFavoriteInteractor: CheckIsUserInFavoriteInteractor): CheckIsUserInFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetUserReposOrFollowingOrFollowerUseCase(
        getUserReposOrFollowingOrFollowerUseCaseInteractor: GetUserReposOrFollowingOrFollowerInteractor
    ): GetUserReposOrFollowingOrFollowerUseCase

}*/
