package com.brainasaservice.reviewbrowser.dagger

import com.brainasaservice.api.dagger.ApiModule
import com.brainasaservice.api.dagger.RepositoryModule
import com.brainasaservice.api.dagger.HttpModule
import com.brainasaservice.reviewbrowser.dagger.module.AppModule
import com.brainasaservice.reviewbrowser.dagger.module.BuilderModule
import com.brainasaservice.reviewbrowser.ui.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuilderModule::class,
    HttpModule::class,
    ApiModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
