package com.fajar.githubuserappdicoding.list_user_and_user_favorite.di

import android.content.Context
import com.fajar.githubuserappdicoding.di.ListUserModuleDependencies
import com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.uiview.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(
    dependencies = [ListUserModuleDependencies::class],
    modules = [ListUserModule::class]
)
interface ListUserComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder

        fun appDependencies(dynamicFeatureModuleDependencies: ListUserModuleDependencies): Builder

        fun build(): ListUserComponent
    }

}