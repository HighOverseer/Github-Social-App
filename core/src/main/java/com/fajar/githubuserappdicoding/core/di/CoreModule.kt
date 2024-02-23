package com.fajar.githubuserappdicoding.core.di

import com.fajar.githubuserappdicoding.core.domain.usecase.CheckIsThemeDarkInteractor
import com.fajar.githubuserappdicoding.core.domain.usecase.CheckIsThemeDarkUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {
    @Binds
    @Singleton
    abstract fun provideCheckIsThemeDarkUseCase(checkIsThemeDarkInteractor: CheckIsThemeDarkInteractor): CheckIsThemeDarkUseCase

}