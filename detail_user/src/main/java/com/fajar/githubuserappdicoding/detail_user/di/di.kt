package com.fajar.githubuserappdicoding.detail_user.di

import android.content.Context
import com.fajar.githubuserappdicoding.DetailUserModuleDependencies
import dagger.hilt.android.EntryPointAccessors

fun initDI(context: Context):DetailUserComponent{
    return DaggerDetailUserComponent.builder()
        .context(context)
        .appDependencies(
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                DetailUserModuleDependencies::class.java
            )
        ).build()
}