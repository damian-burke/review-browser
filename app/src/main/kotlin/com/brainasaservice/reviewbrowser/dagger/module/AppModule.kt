package com.brainasaservice.reviewbrowser.dagger.module

import android.app.Application
import android.content.Context
import com.brainasaservice.common.buildconfig.BuildConfigProvider
import com.brainasaservice.common.scheduler.SchedulerConfiguration
import com.brainasaservice.reviewbrowser.BuildConfig
import com.brainasaservice.reviewbrowser.ui.App
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class AppModule {
    @Singleton
    @Provides
    fun providesApplicationContext(app: App): Context = app

    @Singleton
    @Provides
    fun providesBuildConfigProvider(): BuildConfigProvider = object : BuildConfigProvider {
        override val appVersion: String = BuildConfig.VERSION_NAME

        override val apiBaseUrl: String = BuildConfig.API_URI

        override val timeFormat: String = "MMMM d, YYYY"

        override val debuggable: Boolean = BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun providesSchedulerConfiguration(): SchedulerConfiguration = object : SchedulerConfiguration {
        override val io: Scheduler = Schedulers.io()
        override val computation: Scheduler = Schedulers.computation()
        override val ui: Scheduler = AndroidSchedulers.mainThread()
        override val timer: Scheduler = Schedulers.computation()
    }
}
