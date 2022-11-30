package com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit

import com.ikrima.practice.dicoding.githubuserappdicoding.BuildConfig
import com.ikrima.practice.dicoding.githubuserappdicoding.data.remote.UrlEndPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private var retrofitAPIGitHubUser : Retrofit? = null

    private fun providenHttpLoggingInterceptor() = run {
        HttpLoggingInterceptor().apply {

            apply { level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

            }
        }
    }

    fun getApiClientGitHubUser(): Retrofit? {
        if (retrofitAPIGitHubUser == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(providenHttpLoggingInterceptor())
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(7000, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build()

            retrofitAPIGitHubUser = Retrofit.Builder()
                .baseUrl(UrlEndPoint.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitAPIGitHubUser
    }


}
