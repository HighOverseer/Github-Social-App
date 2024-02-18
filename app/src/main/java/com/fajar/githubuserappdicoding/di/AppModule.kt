package com.fajar.githubuserappdicoding.di

import com.fajar.githubuserappdicoding.domain.usecase.ChangeThemePrefUseCase
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkUseCase
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.GetUserReposOrFollowingOrFollowerUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchInteractor
import com.fajar.githubuserappdicoding.domain.usecase.SearchUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusUseCase
import com.fajar.githubuserappdicoding.domain.usecase.interactor.ChangeThemePrefInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.CheckIsThemeDarkInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.CheckIsUserInFavoriteInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.GetDetailUserInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.GetUserReposOrFollowingOrFollowerUseCaseInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.SearchUserInFavoriteInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.SearchUserInteractor
import com.fajar.githubuserappdicoding.domain.usecase.interactor.ToggleFavoriteStatusInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUserUseCase(searchUserInteractor: SearchUserInteractor): SearchUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckIsThemeDarkUseCase(checkIsThemeDarkInteractor: CheckIsThemeDarkInteractor): CheckIsThemeDarkUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUserInFavoriteUseCase(searchUserInFavoriteInteractor: SearchUserInFavoriteInteractor): SearchUserInFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideChangeThemePrefUseCase(changeThemePrefInteractor: ChangeThemePrefInteractor): ChangeThemePrefUseCase

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
        getUserReposOrFollowingOrFollowerUseCaseInteractor: GetUserReposOrFollowingOrFollowerUseCaseInteractor
    ): GetUserReposOrFollowingOrFollowerUseCase

    //tes
    @Binds
    @ViewModelScoped
    abstract fun provideSearchUser(searchInteractor: SearchInteractor): SearchUseCase

}