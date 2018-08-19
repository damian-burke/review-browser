package com.brainasaservice.api.dagger

import com.brainasaservice.common.buildconfig.BuildConfigProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class HttpModule {
    @Provides
    @Singleton
    fun provideGson(
            buildConfigProvider: BuildConfigProvider
    ): Gson = GsonBuilder().apply {
        setLenient()
        setDateFormat(buildConfigProvider.timeFormat)
    }.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
            buildConfigProvider: BuildConfigProvider
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                    .header("User-Agent", "GetYourGuide")
                    .build()
            chain.proceed(request)
        }

        addInterceptor(HttpLoggingInterceptor { message ->
            Timber.v(message)
        }.apply {
            if(buildConfigProvider.debuggable) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        })
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient, buildConfigProvider: BuildConfigProvider): Retrofit =
            Retrofit.Builder().apply {
                baseUrl(buildConfigProvider.apiBaseUrl)
                addConverterFactory(GsonConverterFactory.create(gson))
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                client(client)
            }.build()
}
