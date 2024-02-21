package com.fajar.githubuserappdicoding

import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.usecase.CheckIsThemeDarkUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ListUserModuleDependencies {
    fun repository(): Repository

    fun usecase():CheckIsThemeDarkUseCase

}