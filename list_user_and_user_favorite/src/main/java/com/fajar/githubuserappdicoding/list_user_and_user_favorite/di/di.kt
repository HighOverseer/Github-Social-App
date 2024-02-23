package com.fajar.githubuserappdicoding.list_user_and_user_favorite.di

import android.content.Context
import com.fajar.githubuserappdicoding.di.ListUserModuleDependencies
import dagger.hilt.android.EntryPointAccessors

fun initDI(context: Context): ListUserComponent {
    return DaggerListUserComponent.builder()
        .context(context)
        .appDependencies(
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                ListUserModuleDependencies::class.java
            )
        ).build()
}