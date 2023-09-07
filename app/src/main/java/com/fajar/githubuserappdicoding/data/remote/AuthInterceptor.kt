package com.fajar.githubuserappdicoding.data.remote

import com.fajar.githubuserappdicoding.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithToken = chain.request().newBuilder()
            .addHeader("Authorization", "token ${BuildConfig.API_TOKEN}")
            .build()
        return chain.proceed(requestWithToken)
    }
}