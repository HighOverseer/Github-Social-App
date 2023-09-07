package com.fajar.githubuserappdicoding.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajar.githubuserappdicoding.Injection

class ViewModelFactory private constructor(
    private val applicationContext: Context,
    private var args: Bundle? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainVM::class.java) -> {
                MainVM(Injection.provideRepos(applicationContext)) as T
            }

            modelClass.isAssignableFrom(DetailVM::class.java) -> {
                args?.let {
                    DetailVM(Injection.provideRepos(applicationContext), it) as T
                } ?: super.create(modelClass)
            }

            modelClass.isAssignableFrom(UserDetailInfoVM::class.java) -> {
                return args?.let {
                    UserDetailInfoVM(Injection.provideRepos(applicationContext), it) as T
                } ?: super.create(modelClass)
            }

            modelClass.isAssignableFrom(SplashVM::class.java) -> {
                SplashVM(Injection.provideRepos(applicationContext)) as T
            }

            else -> super.create(modelClass)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(
            applicationContext: Context,
            args: Bundle? = null
        ): ViewModelFactory {
            return INSTANCE?.also {
                it.args = args
            } ?: synchronized(this) {
                INSTANCE?.also {
                    it.args = args
                } ?: ViewModelFactory(
                    applicationContext,
                    args
                )
            }.also { INSTANCE = it }
        }
    }
}