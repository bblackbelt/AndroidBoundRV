package com.blackbelt.androidboundrv

import android.app.Activity
import com.blackbelt.androidboundrv.di.AppComponent
import android.app.Application
import com.blackbelt.androidboundrv.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

class App : Application() , HasActivityInjector {

    @Inject
    lateinit var mAndroidDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = mAndroidDispatchingInjector
}
