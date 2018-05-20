package com.blackbelt.androidboundrv.di

import com.blackbelt.androidboundrv.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (HelpersModule::class),
    (NetworkModule::class), (BindsModule::class), (ManagersModule::class), (ContributorsModule::class)])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}