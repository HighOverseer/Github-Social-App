package com.fajar.githubuserappdicoding.data.remote.network


import com.fajar.githubuserappdicoding.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    @Volatile
    private var OK_HTTP_CLIENT_INSTANCE: OkHttpClient? = null

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(getClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

   private fun getClient(): OkHttpClient {
        return OK_HTTP_CLIENT_INSTANCE ?: synchronized(this) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()
            okHttpClient
        }.also { OK_HTTP_CLIENT_INSTANCE = it }
    }

}