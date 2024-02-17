package com.fajar.githubuserappdicoding.di

import com.fajar.githubuserappdicoding.domain.usecase.ChangeThemePrefInteractor
import com.fajar.githubuserappdicoding.domain.usecase.ChangeThemePrefUseCase
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkInteractor
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkUseCase
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteInteractor
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserInteractor
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserInFavoriteInteractor
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserInteractor
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusInteractor
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUserUseCase(searchUserInteractor: SearchUserInteractor):SearchUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckIsThemeDarkUseCase(checkIsThemeDarkInteractor: CheckIsThemeDarkInteractor):CheckIsThemeDarkUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUserInFavoriteUseCase(searchUserInFavoriteInteractor: SearchUserInFavoriteInteractor):SearchUserInFavoriteUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideChangeThemePrefUseCase(changeThemePrefInteractor: ChangeThemePrefInteractor):ChangeThemePrefUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetDetailUserUseCase(getDetailUserInteractor: GetDetailUserInteractor):GetDetailUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideToggleFavoriteStatusUseCase(toggleFavoriteStatusInteractor: ToggleFavoriteStatusInteractor):ToggleFavoriteStatusUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideCheckIsUserInFavoriteUseCase(checkIsUserInFavoriteInteractor: CheckIsUserInFavoriteInteractor):CheckIsUserInFavoriteUseCase
}