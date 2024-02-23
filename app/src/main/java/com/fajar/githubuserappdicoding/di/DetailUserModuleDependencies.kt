package com.fajar.githubuserappdicoding.di

import com.fajar.githubuserappdicoding.core.domain.data.Repository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DetailUserModuleDependencies {
    fun repository(): Repository

}