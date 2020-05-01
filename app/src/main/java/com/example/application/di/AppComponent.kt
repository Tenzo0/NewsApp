/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.di

import com.example.application.App
import com.example.application.di.news.NewsComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    ViewModelBuilderModule::class,
    NetworkModule::class,
    SubComponents::class
])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }

    fun inject(application: App)
    fun newsComponent() : NewsComponent.Factory
}

@Module(subcomponents = [NewsComponent::class])
object SubComponents