package com.fajar.githubuserappdicoding.detail_user.di

import android.content.Context
import com.fajar.githubuserappdicoding.detail_user.presentation.uiview.DetailActivity
import com.fajar.githubuserappdicoding.detail_user.presentation.uiview.UserDetailInfoFragment
import com.fajar.githubuserappdicoding.di.DetailUserModuleDependencies
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Component(
    dependencies = [DetailUserModuleDependencies::class],
    modules = [DetailUserModule::class]
)
interface DetailUserComponent {

    fun inject(activity: DetailActivity)
    fun inject(fragment: UserDetailInfoFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder

        fun appDependencies(dynamicFeatureModuleDependencies: DetailUserModuleDependencies): Builder

        fun build(): DetailUserComponent
    }

}