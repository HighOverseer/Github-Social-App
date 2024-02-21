package com.fajar.githubuserappdicoding.list_user_and_user_favorite.di

import androidx.lifecycle.ViewModel
import com.fajar.githubuserappdicoding.core.di.dynamicfeature.ViewModelKey
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.ChangeThemePrefUseCase
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.interactor.SearchUserInteractor
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.SearchUserUseCase
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.domain.usecase.interactor.ChangeThemePrefInteractor
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap


@DisableInstallInCheck
@Module
abstract class ListUserModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel:MainViewModel):ViewModel

    @Binds
    abstract fun provideSearchUserUseCase(searchUserInteractor: SearchUserInteractor): SearchUserUseCase

    @Binds
    abstract fun provideChangeThemePrefUseCase(changeThemePrefInteractor: ChangeThemePrefInteractor):ChangeThemePrefUseCase
}
/*
@Module
@InstallIn(ViewModelComponent::class)
abstract class ListUserModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUserUseCase(searchUserInteractor: SearchUserInteractor): SearchUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideChangeThemePrefUseCase(changeThemePrefInteractor: ChangeThemePrefInteractor):ChangeThemePrefUseCase
}*/
